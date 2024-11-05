package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.core.logging.Loggable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileToSignalTable
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.route.signal.data.model.IncludedFile
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Join
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.transactions.transaction

class IncludedFilesRepository(
    private val database: Database
) : Loggable by Loggable.Default("IncludedFilesRepository") {

    /**
     * This is a list of files, which contains every instance of [successSignalIds]
     * aka list<SignalID>().containsAll(successSignal1,successSignal2)
     */
    private fun getWhiteListedFileIds(successSignalIds: List<Long>): List<Long> {
        var join: Join? = null
        successSignalIds.forEachIndexed { i, id ->
            join = (join ?: InfraredFileToSignalTable)
                .join(
                    otherTable = InfraredFileToSignalTable.alias("F$i"),
                    onColumn = InfraredFileToSignalTable.infraredFileId,
                    otherColumn = InfraredFileToSignalTable.alias("F$i")[InfraredFileToSignalTable.infraredFileId],
                    joinType = JoinType.INNER
                )
        }
        var query = join
            ?.select(InfraredFileToSignalTable.infraredFileId)
            ?.withDistinct(true)
        successSignalIds.forEachIndexed { index, id ->
            query = if (index == 0) {
                query?.where { InfraredFileToSignalTable.signalId eq id }
            } else {
                query?.andWhere {
                    InfraredFileToSignalTable.alias("F$index")[InfraredFileToSignalTable.signalId] eq id
                }
            }
        }
        return when {
            successSignalIds.isEmpty() -> emptyList()
            else -> transaction(database) {
                query?.map { it[InfraredFileToSignalTable.infraredFileId].value }.orEmpty()
            }
        }
    }

    /**
     * If any signal is failed then we skip this file
     */
    private fun getBlackListedFileIds(failedSignalIds: List<Long>): List<Long> {
        return when {
            failedSignalIds.isEmpty() -> emptyList()
            else -> transaction(database) {
                InfraredFileToSignalTable.select(InfraredFileToSignalTable.infraredFileId)
                    .withDistinct(true)
                    .where { InfraredFileToSignalTable.signalId inList failedSignalIds }
                    .map { it[InfraredFileToSignalTable.infraredFileId].value }
            }
        }
    }

    /**
     * Keep only those files, in which signals have been successfully passed
     *
     * SELECT INFRARED_FILE."id", INFRARED_FILE."signal_count"
     * FROM INFRARED_FILE JOIN INFRARED_FILE_TO_SIGNAL on INFRARED_FILE_TO_SIGNAL."infrared_file_id" = INFRARED_FILE."id"
     * group by INFRARED_FILE."id"
     * order by INFRARED_FILE."signal_count" desc
     */
    suspend fun findIncludedFiles(signalRequestModel: SignalRequestModel): List<IncludedFile> {
        info { "#findIncludedFiles invoked" }

        val excludedFileIds = getBlackListedFileIds(
            failedSignalIds = signalRequestModel.failedResults
                .map(SignalRequestModel.SignalResultData::signalId)
        )
        info { "#findIncludedFiles excludedFileIds: $excludedFileIds" }
        val includedFileIds = getWhiteListedFileIds(
            successSignalIds = signalRequestModel.successResults
                .map(SignalRequestModel.SignalResultData::signalId)
        )
        info { "#findIncludedFiles includedFileIds: $includedFileIds" }
        return transaction(database) {
            InfraredFileTable
                // Main query
                .select(InfraredFileTable.id, InfraredFileTable.signalCount)
                .groupBy(InfraredFileTable.id, InfraredFileTable.signalCount)
                .orderBy(InfraredFileTable.signalCount to SortOrder.DESC)
                .where { InfraredFileTable.brandId eq signalRequestModel.brandId }
                .let { nextQuery ->
                    if (includedFileIds.isEmpty()) nextQuery
                    else nextQuery.andWhere { InfraredFileTable.id inList includedFileIds }
                }
                //[3273, 3282, 3283, 3286]
                .let { nextQuery ->
                    if (excludedFileIds.isEmpty()) nextQuery
                    else nextQuery.andWhere { InfraredFileTable.id notInList excludedFileIds }
                }
                .map {
                    val file = IncludedFile(
                        fileId = it[InfraredFileTable.id].value,
                        signalCount = it[InfraredFileTable.signalCount]
                    )
                    file
                }.also {

                    debug { "#got files: ${it.map { it.fileId }}" }
                }
        }
    }

    suspend fun findFallbackFile(signalRequestModel: SignalRequestModel): IncludedFile? {
        // Getting last available file
        return findIncludedFiles(
            signalRequestModel = when {
                signalRequestModel.skippedResults.isNotEmpty() -> {
                    signalRequestModel.copy(
                        skippedResults = signalRequestModel.skippedResults.dropLast(1)
                    )
                }

                signalRequestModel.failedResults.isNotEmpty() -> {
                    signalRequestModel.copy(
                        failedResults = signalRequestModel.failedResults.dropLast(1)
                    )
                }

                else -> {
                    signalRequestModel.copy(
                        successResults = signalRequestModel.successResults.dropLast(1)
                    )
                }
            },
        ).firstOrNull()
    }
}