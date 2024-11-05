package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileToSignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalKeyTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.ifrmvp.model.buttondata.SingleKeyButtonData
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.wrapAsExpression

class SignalRepository(private val database: Database) {
    suspend fun getSignalModel(
        signalRequestModel: SignalRequestModel,
        order: CategoryConfiguration.OrderModel,
        includedFiles: List<Long>
    ): SignalModel? {
        return transaction(database) {
            SignalTable
                .join(
                    otherTable = InfraredFileToSignalTable,
                    joinType = JoinType.LEFT,
                    onColumn = SignalTable.id,
                    otherColumn = InfraredFileToSignalTable.signalId
                )
                .join(
                    otherTable = InfraredFileTable,
                    joinType = JoinType.LEFT,
                    onColumn = InfraredFileToSignalTable.infraredFileId,
                    otherColumn = InfraredFileTable.id
                )
                .join(
                    otherTable = SignalKeyTable,
                    joinType = JoinType.LEFT,
                    onColumn = SignalTable.id,
                    otherColumn = SignalKeyTable.signalId
                )
                .selectAll()
                .where { SignalTable.brandId eq signalRequestModel.brandId }
                .let { query ->
                    val singleButtonData = order.data as? SingleKeyButtonData
                    when (val identifier = singleButtonData?.keyIdentifier) {
                        is IfrKeyIdentifier.Sha256 -> {
                            query.andWhere {
                                SignalKeyTable.deviceKey.eq(order.key)
                                    .or { SignalKeyTable.hash.eq(identifier.hash) }
                            }
                        }

                        is IfrKeyIdentifier.Name -> {
                            query.andWhere {
                                SignalKeyTable.remoteKeyName.eq(identifier.name)
                            }
                        }

                        else -> {
                            query.andWhere { SignalKeyTable.deviceKey.eq(order.key) }
                        }
                    }
                }
                .andWhere { SignalKeyTable.deviceKey eq order.key }
                .andWhere { SignalKeyTable.signalId eq SignalTable.id }
                .let { query ->
                    if (includedFiles.isEmpty()) query
                    else query.andWhere { InfraredFileToSignalTable.infraredFileId inList includedFiles }

                }
                .let { query ->
                    val failedSignalIds = signalRequestModel.failedResults
                        .map(SignalRequestModel.SignalResultData::signalId)
                    if (failedSignalIds.isEmpty()) {
                        query
                    } else {
                        query.andWhere { SignalTable.id.notInList(failedSignalIds) }
                    }
                }
                .let { query ->
                    val successfulSignalIds = signalRequestModel.successResults
                        .map(SignalRequestModel.SignalResultData::signalId)
                    if (successfulSignalIds.isEmpty()) {
                        query
                    } else {
                        query.andWhere { SignalTable.id.notInList(successfulSignalIds) }
                    }
                }
                .let { query ->
                    val successfulSignalIds = signalRequestModel.successResults
                        .map(SignalRequestModel.SignalResultData::signalId)
                    if (successfulSignalIds.isEmpty()) {
                        query
                    } else {
                        query.andWhere {
                            InfraredFileToSignalTable.infraredFileId inSubQuery InfraredFileToSignalTable
                                .select(InfraredFileToSignalTable.infraredFileId)
                                .where { InfraredFileToSignalTable.signalId inList successfulSignalIds }
                        }
                    }
                }
                .orderBy(InfraredFileTable.signalCount to SortOrder.DESC)
                .limit(1)
                .map {
                    SignalModel(
                        id = it[SignalTable.id].value,
                        remote = SignalModel.FlipperRemote(
                            name = "empty",
                            type = it[SignalTable.type],
                            protocol = it[SignalTable.protocol],
                            address = it[SignalTable.address],
                            command = it[SignalTable.command],
                            frequency = it[SignalTable.frequency],
                            dutyCycle = it[SignalTable.dutyCycle],
                            data = it[SignalTable.data],
                        )
                    )
                }
                .firstOrNull()
        }
    }
}