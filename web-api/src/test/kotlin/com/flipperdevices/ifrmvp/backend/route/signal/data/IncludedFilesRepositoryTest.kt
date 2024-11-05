package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.route.signal.data.model.IncludedFile
import com.flipperdevices.ifrmvp.model.buttondata.UnknownButtonData
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class IncludedFilesRepositoryTest {
    private var signalApiModule: SignalApiModule? = null
    private val requireSignalApiModule: SignalApiModule
        get() = signalApiModule ?: error("Forget to register module")

    @BeforeTest
    fun setup() {
        signalApiModule = SignalApiModule.Default(
            signalDbConnection = EnvKonfig.signalDatabaseConnection
        )
    }

    @AfterTest
    fun tearDown() {
        signalApiModule?.database?.run(TransactionManager::closeAndUnregister)
        signalApiModule = null
    }

    fun stubOrderModel(key: DeviceKey) = CategoryConfiguration.OrderModel(
        message = "stub",
        index = key.ordinal,
        key = key,
        data = UnknownButtonData
    )

    @Test
    fun `test included files are correct`(): Unit = runBlocking {

        val categoryId = transaction(requireSignalApiModule.database) {
            CategoryTable.select(CategoryTable.id)
                .where { CategoryTable.folderName eq CategoryType.TVS.folderName }
                .limit(1)
                .map { it[CategoryTable.id] }
                .first()
                .value
        }
        val brandId = transaction(requireSignalApiModule.database) {
            BrandTable.select(BrandTable.id)
                .where { BrandTable.categoryId eq categoryId }
                .andWhere { BrandTable.folderName eq "Skyworth" }
                .limit(1)
                .map { it[BrandTable.id] }
                .first()
                .value
        }
        val includedFilesRepository = IncludedFilesRepository(requireSignalApiModule.database)
        val signalRepository = SignalRepository(requireSignalApiModule.database)

        SignalRequestModel(brandId = brandId).let { request1 ->
            val includedFiles1 = includedFilesRepository.findIncludedFiles(signalRequestModel = request1)
            assertEquals(31, includedFiles1.size)
            val signal1 = signalRepository.getSignalModel(
                signalRequestModel = request1,
                order = stubOrderModel(DeviceKey.PWR),
                includedFiles = includedFiles1.map(IncludedFile::fileId)
            )
            assertNotNull(signal1)
            assertEquals("parsed", signal1.remote.type)
            assertEquals("00 00 00 00", signal1.remote.address)
            assertEquals("0C 00 00 00", signal1.remote.command)
            assertEquals("RC5", signal1.remote.protocol)
            SignalRequestModel(
                brandId = brandId,
                successResults = listOf(SignalRequestModel.SignalResultData(signal1.id))
            ).let { request2 ->
                val includedFiles2 = includedFilesRepository.findIncludedFiles(signalRequestModel = request2)
                assertEquals(4, includedFiles2.size)
                val signal2 = signalRepository.getSignalModel(
                    signalRequestModel = request1,
                    order = stubOrderModel(DeviceKey.VOL_UP),
                    includedFiles = includedFiles1.map(IncludedFile::fileId)
                )
                assertNotNull(signal2)
                assertEquals("parsed", signal2.remote.type)
                assertEquals("00 00 00 00", signal2.remote.address)
                assertEquals("10 00 00 00", signal2.remote.command)
                assertEquals("RC5", signal2.remote.protocol)
                SignalRequestModel(
                    brandId = brandId,
                    successResults = listOf(
                        SignalRequestModel.SignalResultData(signal1.id),
                        SignalRequestModel.SignalResultData(signal2.id)
                    )
                ).let { request3 ->
                    val includedFiles3 = includedFilesRepository.findIncludedFiles(signalRequestModel = request3)
                    assertEquals(3, includedFiles3.size)
                    val signal3 = signalRepository.getSignalModel(
                        signalRequestModel = request1,
                        order = stubOrderModel(DeviceKey.VOL_DOWN),
                        includedFiles = includedFiles1.map(IncludedFile::fileId)
                    )
                    assertNotNull(signal3)
                }
            }
        }
    }
}