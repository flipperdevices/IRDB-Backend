package com.flipperdevices.ifrmvp.backend.envkonfig.model

sealed class DBConnection(val driver: String) {
    class H2(val path: String) : DBConnection("org.h2.Driver")
    class Postgres(
        val host: String,
        val port: String,
        val user: String,
        val password: String,
        val name: String
    ) : DBConnection("org.postgresql.Driver")
}
