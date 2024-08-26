package com.flipperdevices.ifrmvp.kenerator.ui.category.airpurifier

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.kenerator.ui.category.fan.FanMap
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

class AirPurifierUiGenerator {
    private val map: BrandMap = FanMap()

    fun generate(signals: List<InfraredRemote>): PagesLayout {
        return PagesLayout(
            pages = listOf(
                PageLayout(
                    buttons = listOfNotNull(
                        signals.findByKey(DeviceKey.PWR)
                            ?.let { PowerButtonData(keyIdentifier = Name(it.name)) },
                        signals.findByKey(DeviceKey.OFF)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "OFF") },
                        signals.findByKey(DeviceKey.MUTE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MUTE) },
                        signals.findByKey(DeviceKey.MODE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "MODE") },
                        signals.findByKey(DeviceKey.FAN_SPEED)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FS") },
                        signals.findByKey(DeviceKey.SWING)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SWING") },
                        signals.findByKey(DeviceKey.COLD_WIND)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "COLD_WIND") },
                        signals.findByKey(DeviceKey.COOL)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "COOL") },
                        signals.findByKey(DeviceKey.WIND_TYPE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "WT") },
                        signals.findByKey(DeviceKey.SHAKE_WIND)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SHK") },
                        signals.findByKey(DeviceKey.WIND_SPEED)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "W+") },
                        signals.findByKey(DeviceKey.FAN_SPEED_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SP-") },
                        signals.findByKey(DeviceKey.SLEEP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SLEEP") },
                        signals.findByKey(DeviceKey.FAN_SPEED_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SP+") },
                        signals.findByKey(DeviceKey.TIMER_REDUCE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TM-") },
                        signals.findByKey(DeviceKey.TIMER_ADD)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TM+") },
                        signals.findByKey(DeviceKey.TIMER)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "T") },
                        signals.findByKey(DeviceKey.OSCILLATE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "OSC") },
                        signals.findByKey(DeviceKey.TEMPERATURE_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "T-") },
                        signals.findByKey(DeviceKey.TEMPERATURE_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "T+") },
                        signals.findByKey(DeviceKey.HEAT_REDUCE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "H-") },
                        signals.findByKey(DeviceKey.HEAT_ADD)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "H+") },
                    ).also { println("Find buttons: ${it.map { it.type }}") }.let(map::transform)
                )
            )
        )
    }
}