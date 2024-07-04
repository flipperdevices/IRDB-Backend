package com.flipperdevices.ifrmvp.backend.envkonfig

import com.flipperdevices.ifrmvp.backend.envkonfig.model.DBConnection
import java.net.InetAddress

object EnvKonfig {
    internal enum class DBType {
        H2
    }

    val FBACKEND_HOST: String
        get() = KSystem.getenvOrNull("FBACKEND_HOST")
//            ?: InetAddress.getLocalHost().hostAddress
            ?: "192.168.0.100"
    val FBACKEND_PORT: Int
        get() = KSystem.getenvOrNull("FBACKEND_PORT")
            ?.toIntOrNull()
            ?: 8080
    internal val FBACKEND_DB_TYPE: DBType
        get() = KSystem.getenvOrNull("FBACKEND_DB_TYPE")
            ?.let { type -> DBType.entries.firstOrNull { entry -> entry.name == type } }
            ?: DBType.H2
    internal val FBACKEND_DB_NAME: String
        get() = KSystem.getenvOrNull("FBACKEND_DB_NAME")
            ?: "UNNAMED"

    val signalDatabaseConnection: DBConnection
        get() = when (EnvKonfig.FBACKEND_DB_TYPE) {
            EnvKonfig.DBType.H2 -> DBConnection.H2(EnvKonfig.FBACKEND_DB_NAME)
        }
}
