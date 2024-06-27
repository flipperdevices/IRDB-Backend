package com.flipperdevices.ifrmvp.backend.route.signal.mapping

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import org.jetbrains.exposed.sql.ResultRow

internal object SignalModelMapper {
    fun ResultRow.toSignalModel(): SignalModel {
        val signalResultRow = this
        return SignalModel(
            id = signalResultRow[SignalTable.id].value,
            irFileId = signalResultRow[SignalTable.ifrFileRef].value,
            brandId = signalResultRow[SignalTable.brandRef].value,
            categoryId = signalResultRow[SignalTable.categoryRef].value,
            name = signalResultRow[SignalTable.name],
            type = signalResultRow[SignalTable.type],
            protocol = signalResultRow[SignalTable.protocol],
            address = signalResultRow[SignalTable.address],
            command = signalResultRow[SignalTable.command],
            frequency = signalResultRow[SignalTable.frequency],
            dutyCycle = signalResultRow[SignalTable.dutyCycle],
            data = signalResultRow[SignalTable.data],
            hash = signalResultRow[SignalTable.hash]
        )
    }
}
