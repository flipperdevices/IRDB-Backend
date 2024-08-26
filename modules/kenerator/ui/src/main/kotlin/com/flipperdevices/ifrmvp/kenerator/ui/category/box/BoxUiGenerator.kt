package com.flipperdevices.ifrmvp.kenerator.ui.category.box

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

class BoxUiGenerator {
    private val map: BrandMap = TvMap()

    fun generate(signals: List<InfraredRemote>): PagesLayout {
        return PagesLayout(
            pages = listOf(
                PageLayout(
                    buttons = listOfNotNull(
                        signals.findByKey(DeviceKey.PWR)
                            ?.let { PowerButtonData(keyIdentifier = Name(it.name)) },
                        signals.findByKey(DeviceKey.FOCUS_LESS)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "F-") },
                        signals.findByKey(DeviceKey.FOCUS_MORE)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "F+") },
                        signals.findByKey(DeviceKey.NEXT)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.NEXT) },
                        signals.findByKey(DeviceKey.PREVIOUS)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.PREVIOUS) },
                        signals.findByKey(DeviceKey.REW)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "REV") },
                        signals.findByKey(DeviceKey.TV)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "TV") },
                        signals.findByKey(DeviceKey.AUX)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "AUX") },
                        signals.findByKey(DeviceKey.INFO)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.INFO) },
                        signals.findByKey(DeviceKey.EXIT)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.EXIT) },
                        signals.findByKey(DeviceKey.MENU)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MENU) },
                        signals.findByKey(DeviceKey.HOME)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.HOME)},
                        signals.findByKey(DeviceKey.BACK)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.BACK)},
                        signals.findByKey(DeviceKey.PAUSE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.PAUSE) },
                        signals.findByKey(DeviceKey.PLAY)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.PLAY) },
                        signals.findByKey(DeviceKey.STOP)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.STOP) },
                        signals.findByKey(DeviceKey.MUTE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MUTE) },
                        signals.findByKey(DeviceKey.SET)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "SET") },
                        signals.findByKey(DeviceKey.DELETE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.DELETE) },
                        signals.findByKey(DeviceKey.VOD)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "VOD") },
                        signals.findByKey(DeviceKey.LIVE_TV)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.LIVE_TV) },
                        signals.findByKey(DeviceKey.FAVORITE)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.FAVORITE) },
                        signals.findVolumeButton()
                            ?.let { VolumeButtonData(Name(it.add.name), Name(it.reduce.name)) },
                        signals.findChannelButton()
                            ?.let { ChannelButtonData(Name(it.next.name), Name(it.prev.name)) },
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
                        signals.findNavigationRemote()
                            ?.let {
                                NavigationButtonData(
                                    upKeyIdentifier = Name(it.up.name),
                                    leftKeyIdentifier = Name(it.left.name),
                                    rightKeyIdentifier = Name(it.right.name),
                                    downKeyIdentifier = Name(it.down.name),
                                )
                            },
                    ).let(map::transform)
                )
            )
        )
    }
}