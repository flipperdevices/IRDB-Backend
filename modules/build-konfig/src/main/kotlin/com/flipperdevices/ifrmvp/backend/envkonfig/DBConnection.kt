package com.flipperdevices.ifrmvp.backend.envkonfig

sealed class DBConnection(val driver: String) {
    class SQLite(val name: String) : DBConnection("org.sqlite.JDBC") {
        val url = "jdbc:sqlite:$name.db"
    }

    class H2(val name: String) : DBConnection("org.h2.Driver")

    class MySql(
        val url: String,
        val user: String,
        val password: String
    ) : DBConnection("com.mysql.cj.jdbc.Driver")
}
