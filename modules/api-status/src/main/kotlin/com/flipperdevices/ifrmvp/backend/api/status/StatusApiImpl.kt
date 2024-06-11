package com.flipperdevices.ifrmvp.backend.api.status

import com.flipperdevices.ifrmvp.backend.api.status.db.StatusTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.random.Random

internal class StatusApiImpl(private val database: Database) : StatusApi {
    override suspend fun insertAndGetId(): Long = transaction(database) {
        StatusTable.insertAndGetId {
            it[randomInt] = Random.nextInt()
        }.value
    }
}
