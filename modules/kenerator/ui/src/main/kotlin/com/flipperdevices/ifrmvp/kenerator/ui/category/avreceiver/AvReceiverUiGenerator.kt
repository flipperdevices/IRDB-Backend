package com.flipperdevices.ifrmvp.kenerator.ui.category.avreceiver

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.kenerator.ui.category.tv.TvMap
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

class AvReceiverUiGenerator {
    private val map: BrandMap = TvMap()

    fun generate(signals: List<InfraredRemote>): PagesLayout {
        return PagesLayout(
            pages = listOf(
                PageLayout(
                    buttons = listOfNotNull(
                        signals.findByKey(DeviceKey.PWR)
                            ?.let { PowerButtonData(keyIdentifier = Name(it.name)) },
                        signals.findByKey(DeviceKey.MUTE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MUTE) },
                        signals.findVolumeButton()
                            ?.let { VolumeButtonData(Name(it.add.name), Name(it.reduce.name)) },
                        signals.findChannelButton()
                            ?.let { ChannelButtonData(Name(it.next.name), Name(it.prev.name)) },
                        signals.findNavigationRemote()
                            ?.let {
                                NavigationButtonData(
                                    upKeyIdentifier = Name(it.up.name),
                                    leftKeyIdentifier = Name(it.left.name),
                                    rightKeyIdentifier = Name(it.right.name),
                                    downKeyIdentifier = Name(it.down.name),
                                )
                            },
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
                        signals.findByKey(DeviceKey.BRIGHTNESS_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "BR+") },
                        signals.findByKey(DeviceKey.BRIGHTNESS_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "BR-") },
                        signals.findByKey(DeviceKey.MUTE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MUTE) },
                        signals.findByKey(DeviceKey.PAUSE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "PAUSE") },
                        signals.findByKey(DeviceKey.PLAY)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "PLAY") },
                        signals.findByKey(DeviceKey.STOP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "STOP") },
                        signals.findByKey(DeviceKey.INFO)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.INFO) },
                        signals.findByKey(DeviceKey.EXIT)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "EXIT") },
                        signals.findByKey(DeviceKey.MENU)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "MENU") },
                        signals.findByKey(DeviceKey.HOME)
                            ?.let {IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.HOME)},
                        signals.findByKey(DeviceKey.BACK)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.BACK)},
                        signals.findByKey(DeviceKey.REW)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "REW") },
                        signals.findByKey(DeviceKey.AUX)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "AUX") },
                        signals.findByKey(DeviceKey.TV)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TV") },
                        signals.findByKey(DeviceKey.NEXT)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "NEXT") },
                        signals.findByKey(DeviceKey.PREVIOUS)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "PREV") },
                        signals.findByKey(DeviceKey.RESET)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "RESET") },
                        signals.findByKey(DeviceKey.ZOOM_UP)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "Z+") },
                        signals.findByKey(DeviceKey.ZOOM_DOWN)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "Z-") },
                    ).let(map::transform)
                )
            )
        )
    }
}