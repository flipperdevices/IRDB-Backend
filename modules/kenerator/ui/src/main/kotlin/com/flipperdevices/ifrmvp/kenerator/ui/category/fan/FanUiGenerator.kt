package com.flipperdevices.ifrmvp.kenerator.ui.category.fan

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.kenerator.ui.core.BrandMap
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findByKey
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findChannelButton
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findNavigationRemote
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findOkNavigationRemote
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findVolumeButton
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier.Name
import com.flipperdevices.ifrmvp.model.PageLayout
import com.flipperdevices.ifrmvp.model.PagesLayout
import com.flipperdevices.ifrmvp.model.buttondata.ChannelButtonData
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData.IconType
import com.flipperdevices.ifrmvp.model.buttondata.NavigationButtonData
import com.flipperdevices.ifrmvp.model.buttondata.OkNavigationButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData
import com.flipperdevices.ifrmvp.model.buttondata.VolumeButtonData
import com.flipperdevices.infrared.editor.model.InfraredRemote

class FanUiGenerator {
    private val map: BrandMap = FanMap()

    fun generate(signals: List<InfraredRemote>): PagesLayout {
        return PagesLayout(
            pages = listOf(
                PageLayout(
                    buttons = listOfNotNull(
                        signals.findByKey(DeviceKey.PWR)
                            ?.let { PowerButtonData(keyIdentifier = Name(it.name)) },
                        signals.findByKey(DeviceKey.SHAKE_WIND)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SHAKE_WIND") },
                        signals.findByKey(DeviceKey.WIND_TYPE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "WIND_TYPE") },
                        signals.findByKey(DeviceKey.WIND_SPEED)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "WIND_SPEED") },
                        signals.findByKey(DeviceKey.FAN_SPEED)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_SPEED") },
                        signals.findByKey(DeviceKey.FAN_SPEED_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_SPEED_UP") },
                        signals.findByKey(DeviceKey.FAN_SPEED_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_SPEED_DOWN") },
                        signals.findByKey(DeviceKey.MUTE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "MUTE") },
                        signals.findByKey(DeviceKey.TEMPERATURE_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TEMPERATURE_UP") },
                        signals.findByKey(DeviceKey.TEMPERATURE_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TEMPERATURE_DOWN") },
                        signals.findByKey(DeviceKey.ENERGY_SAVE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "ENERGY_SAVE") },
                        signals.findByKey(DeviceKey.MODE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "MODE") },
                        signals.findByKey(DeviceKey.TIMER)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TIMER") },
                        signals.findByKey(DeviceKey.COLD_WIND)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "COLD_WIND") },
                    ).let(map::transform)
                )
            )
        )
    }
}