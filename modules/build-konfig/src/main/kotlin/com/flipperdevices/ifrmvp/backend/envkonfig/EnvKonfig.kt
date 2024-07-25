package com.flipperdevices.ifrmvp.backend.envkonfig

import com.flipperdevices.ifrmvp.backend.buildkonfig.BuildKonfig
import com.flipperdevices.ifrmvp.backend.envkonfig.model.DBConnection
import java.net.InetAddress

object EnvKonfig {
    internal enum class DBType {
        H2, POSTGRES
    }

    val IR_DATABASE_PATH: String
        get() = KSystem.getenvOrNull("IR_FOLDER_PATH")
            ?: BuildKonfig.IR_FOLDER_PATH

    val FBACKEND_HOST: String
        get() = KSystem.getenvOrNull("FBACKEND_HOST")
            ?: InetAddress.getLocalHost().hostAddress

    val FBACKEND_PORT: Int
        get() = KSystem.getenvOrNull("FBACKEND_PORT")
            ?.toIntOrNull()
            ?: 8080

    private object DbKonfig {
        val FBACKEND_DB_TYPE: DBType
            get() = KSystem.getenvOrNull("FBACKEND_DB_TYPE")
                ?.let { type -> DBType.entries.firstOrNull { entry -> entry.name == type } }
                ?: DBType.H2

        val FBACKEND_DB_FULL_PATH: String
            get() = KSystem.getenvOrNull("DB_FULL_PATH")
                ?: BuildKonfig.FALLBACK_DB_FULL_PATH

        val DB_NAME: String
            get() = KSystem.requireEnv("DB_NAME")

        val DB_HOST: String
            get() = KSystem.requireEnv("DB_HOST")

        val DB_PORT: String
            get() = KSystem.requireEnv("DB_PORT")

        val DB_USER: String
            get() = KSystem.requireEnv("DB_USER")

        val DB_PASSWORD: String
            get() = KSystem.requireEnv("DB_PASSWORD")
    }

    val signalDatabaseConnection: DBConnection
        get() = when (DbKonfig.FBACKEND_DB_TYPE) {
            DBType.H2 -> DBConnection.H2(
                path = DbKonfig.FBACKEND_DB_FULL_PATH
            )

            DBType.POSTGRES -> DBConnection.Postgres(
                host = DbKonfig.DB_HOST,
                port = DbKonfig.DB_PORT,
                user = DbKonfig.DB_USER,
                password = DbKonfig.DB_PASSWORD,
                name = DbKonfig.DB_NAME
            )
        }
}
