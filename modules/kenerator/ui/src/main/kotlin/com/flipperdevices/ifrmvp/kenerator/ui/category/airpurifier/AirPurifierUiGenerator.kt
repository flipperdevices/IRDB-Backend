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
                        signals.findByKey(DeviceKey.SWING)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SWING") },
                        signals.findByKey(DeviceKey.OFF)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "OFF") },
                        signals.findByKey(DeviceKey.FAN_SPEED)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAN_SPEED") },
                        signals.findByKey(DeviceKey.HEAT_ADD)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "HEAT_ADD") },
                        signals.findByKey(DeviceKey.HEAT_REDUCE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "HEAT_REDUCE") },
                        signals.findByKey(DeviceKey.FAN_SPEED_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "HEAT_REDUCE") },
                        signals.findByKey(DeviceKey.FAN_SPEED_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "HEAT_REDUCE") },
                        signals.findByKey(DeviceKey.TEMPERATURE_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "HEAT_REDUCE") },
                        signals.findByKey(DeviceKey.TEMPERATURE_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "HEAT_REDUCE") },
                    ).let(map::transform)
                )
            )
        )
    }
}