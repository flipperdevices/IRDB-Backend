package com.flipperdevices.ifrmvp.backend.envkonfig.di.factory

import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import ru.astrainteractive.klibs.kdi.Factory

internal object RatingDBConnectionFactory : Factory<DBConnection> {
    override fun create(): DBConnection {
        return DBConnectionFactory(prefix = "rating").create()
    }
}
