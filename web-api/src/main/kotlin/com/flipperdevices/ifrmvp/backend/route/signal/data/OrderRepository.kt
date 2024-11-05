package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalNameAliasTable
import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.generator.config.device.api.DeviceKeyNamesProvider.Companion.getKey
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.orWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepository(private val database: Database) {
    fun findOrder(
        signalRequestModel: SignalRequestModel,
        categoryType: CategoryType,
        recursionLevel: Int
    ): CategoryConfiguration.OrderModel? {
        val skippedKeys = transaction(database) {
            SignalNameAliasTable
                .selectAll()
                .where { SignalNameAliasTable.id inList signalRequestModel.skippedResults.map(SignalRequestModel.SignalResultData::signalId) }
                .orWhere { SignalNameAliasTable.id inList signalRequestModel.successResults.map(SignalRequestModel.SignalResultData::signalId) }
//                .orWhere { SignalNameAliasTable.id inList signalRequestModel.failedResults.map(SignalRequestModel.SignalResultData::signalId) }
                .mapNotNull {
                    val keyName = it[SignalNameAliasTable.signalName]
                    AnyDeviceKeyNamesProvider.getKey(keyName)
                }.distinct()
        }

        return ParserPathResolver
            .categoryConfiguration(categoryType.folderName)
            .orders
            .filter { !skippedKeys.contains(it.key) }
            .getOrNull(recursionLevel)
    }
}
