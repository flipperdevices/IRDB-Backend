package com.flipperdevices.ifrmvp.backend.api.status.db

import org.jetbrains.exposed.dao.id.LongIdTable

internal object StatusTable : LongIdTable("status") {
    val randomInt = integer("randomInt")
}
