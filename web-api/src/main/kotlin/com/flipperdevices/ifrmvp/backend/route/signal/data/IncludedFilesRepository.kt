package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileToSignalTable
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.route.signal.data.model.IncludedFile
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.transactions.transaction

class IncludedFilesRepository(private val database: Database) {
    /**
     * Keep only those files, in which signals have been successfully passed
     *
     * SELECT INFRARED_FILE."id", INFRARED_FILE."signal_count"
     * FROM INFRARED_FILE JOIN INFRARED_FILE_TO_SIGNAL on INFRARED_FILE_TO_SIGNAL."infrared_file_id" = INFRARED_FILE."id"
     * group by INFRARED_FILE."id"
     * order by INFRARED_FILE."signal_count" desc
     */
    suspend fun findIncludedFiles(signalRequestModel: SignalRequestModel): List<IncludedFile> {
        val excludedFileIds = transaction(database) {
            InfraredFileToSignalTable.select(InfraredFileToSignalTable.infraredFileId)
                .where {
                    InfraredFileToSignalTable.signalId inList
                            signalRequestModel.failedResults
                                .map(SignalRequestModel.SignalResultData::signalId)
                }.map { it[InfraredFileToSignalTable.infraredFileId].value }
        }
        return transaction(database) {
            InfraredFileTable
                .join(
                    otherTable = InfraredFileToSignalTable,
                    onColumn = InfraredFileTable.id,
                    otherColumn = InfraredFileToSignalTable.infraredFileId,
                    joinType = JoinType.LEFT
                )
                .select(InfraredFileTable.id, InfraredFileTable.signalCount)
                .groupBy(InfraredFileTable.id)
                .orderBy(InfraredFileTable.signalCount to SortOrder.DESC)
                .where { InfraredFileTable.brandId eq signalRequestModel.brandId }
                .let {
                    val successSignalIds = signalRequestModel
                        .successResults
                        .map(SignalRequestModel.SignalResultData::signalId)
                    var nextQuery = it
                    nextQuery = if (successSignalIds.isNotEmpty()) {
                        nextQuery.andWhere {
                            InfraredFileToSignalTable
                                .signalId
                                .inList(successSignalIds)
                        }
                    } else {
                        nextQuery
                    }
                    val failedSignalIds = signalRequestModel
                        .failedResults
                        .map(SignalRequestModel.SignalResultData::signalId)
                    if (failedSignalIds.isNotEmpty()) {
                        nextQuery.andWhere {
                            InfraredFileToSignalTable
                                .infraredFileId
                                .notInList(excludedFileIds)
                        }
                    } else {
                        nextQuery
                    }
                }
                .map {
                    IncludedFile(
                        fileId = it[InfraredFileTable.id].value,
                        signalCount = it[InfraredFileTable.signalCount]
                    )
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