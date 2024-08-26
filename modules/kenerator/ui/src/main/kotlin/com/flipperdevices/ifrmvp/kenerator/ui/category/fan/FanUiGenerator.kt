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

                        signals.findByKey(DeviceKey.MUTE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MUTE)},
                        signals.findByKey(DeviceKey.MODE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "MODE") },
                        signals.findByKey(DeviceKey.FAN_MEDIUM)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_MEDIUM") },
                        signals.findByKey(DeviceKey.FAN_HIGH)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_HIGH") },
                        signals.findByKey(DeviceKey.FAN_LOW)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_LOW") },
                        signals.findByKey(DeviceKey.LIGHT)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "LIGHT") },
                        signals.findByKey(DeviceKey.FAN_SPEED_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_SPEED_UP") },
                        signals.findByKey(DeviceKey.TIMER_REDUCE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TIMER_REDUCE") },
                        signals.findByKey(DeviceKey.OSCILLATE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "OSCILLATE") },
                        signals.findByKey(DeviceKey.TIMER)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TIMER") },
                        signals.findByKey(DeviceKey.TIMER_ADD)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TIMER_ADD") },
                        signals.findByKey(DeviceKey.TEMPERATURE_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TEMPERATURE_DOWN") },
                        signals.findByKey(DeviceKey.TEMPERATURE_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TEMPERATURE_UP") },
                        signals.findByKey(DeviceKey.ENERGY_SAVE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "ENERGY_SAVE") },
                        signals.findOkNavigationRemote()
                            ?.let {
                                OkNavigationButtonData(
                                    upKeyIdentifier = Name(it.up.name),
                                    leftKeyIdentifier = Name(it.left.name),
                                    rightKeyIdentifier = Name(it.right.name),
                                    downKeyIdentifier = Name(it.down.name),
                                    okKeyIdentifier = Name(it.ok.name)
                                )
                            },
                    ).let(map::transform)
                )
            )
        )
    }
}