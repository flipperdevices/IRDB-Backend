package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.model.RawIfrRemote
import com.flipperdevices.infrared.editor.model.InfraredRemote
import kotlinx.coroutines.coroutineScope

internal class InfraredSignalsFiller(
    private val signalTableApi: SignalTableApi,
) {

    suspend fun fill(model: Model) = coroutineScope {
        with(model) {
            remotes.map { remote ->
                val parsed = remote as? InfraredRemote.Parsed
                val raw = remote as? InfraredRemote.Raw
                val rawRemote = RawIfrRemote(
                    name = remote.name,
                    type = remote.type,
                    protocol = parsed?.protocol,
                    address = parsed?.address,
                    command = parsed?.command,
                    frequency = raw?.frequency,
                    dutyCycle = raw?.dutyCycle,
                    data = raw?.data
                )
                val signalId = signalTableApi.addSignal(
                    brandId = brandId,
                    remote = rawRemote,
                )
                signalTableApi.linkFileAndSignal(
                    infraredFileId = ifrFileId,
                    signalId = signalId
                )
            }
        }
    }

    data class Model(
        val ifrFileId: Long,
        val categoryId: Long,
        val brandId: Long,
        val remotes: List<InfraredRemote>,
        val categoryName: String,
        val brandName: String,
        val ifrFolderName: String
    )
}
