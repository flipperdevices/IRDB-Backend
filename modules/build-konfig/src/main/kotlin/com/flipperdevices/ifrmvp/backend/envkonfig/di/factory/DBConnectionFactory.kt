package com.flipperdevices.ifrmvp.backend.envkonfig.di.factory

import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import com.flipperdevices.ifrmvp.backend.envkonfig.SystemExt
import org.slf4j.Logger
import ru.astrainteractive.klibs.kdi.Factory

internal class DBConnectionFactory(
    private val prefix: String,
) : Factory<DBConnection> {
    private val logger = java.util.logging.Logger.getLogger("DBConnectionFactory")
    override fun create(): DBConnection {
        return runCatching {
            DBConnection.MySql(
                url = SystemExt.requireLocalProperty("$prefix.MY_SQL.URL"),
                user = SystemExt.requireLocalProperty("$prefix.MY_SQL.USER"),
                password = SystemExt.requireLocalProperty("$prefix.MY_SQL.PASSWORD")
            )
        }.onFailure {
            logger.warning("MySql connection not configured for $prefix: ${it.message}")
        }.getOrNull() ?: DBConnection.H2(prefix)
    }
}
