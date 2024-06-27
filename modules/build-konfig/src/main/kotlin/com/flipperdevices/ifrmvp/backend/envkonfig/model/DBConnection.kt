package com.flipperdevices.ifrmvp.backend.envkonfig.model

sealed class DBConnection(val driver: String) {
    class H2(val name: String) : DBConnection("org.h2.Driver")
}
