package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalOrderTable

internal class CounterRepository {

    fun countOrders(ifrFileId: Long): Long {
        return SignalOrderTable
            .select(SignalOrderTable.id)
            .where { SignalOrderTable.ifrFileId eq ifrFileId }
            .count()
    }
}
