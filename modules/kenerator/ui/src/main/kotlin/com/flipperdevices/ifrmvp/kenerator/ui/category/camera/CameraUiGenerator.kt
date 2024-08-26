package com.flipperdevices.ifrmvp.kenerator.ui.category.camera

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
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData
import com.flipperdevices.ifrmvp.model.buttondata.VolumeButtonData
import com.flipperdevices.infrared.editor.model.InfraredRemote

class CameraUiGenerator {
    private val map: BrandMap = CameraMap()

    fun generate(signals: List<InfraredRemote>): PagesLayout {
        return PagesLayout(
            pages = listOf(
                PageLayout(
                    buttons = listOfNotNull(
                        signals.findByKey(DeviceKey.PWR)
                            ?.let { PowerButtonData(keyIdentifier = Name(it.name)) },
                        signals.findByKey(DeviceKey.SHUTTER)
                            ?.let { ShutterButtonData(keyIdentifier = Name(it.name)) },
                        signals.findByKey(DeviceKey.ZOOM_UP)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.ZOOM_IN) },
                        signals.findByKey(DeviceKey.ZOOM_DOWN)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.ZOOM_OUT) },
                        signals.findByKey(DeviceKey.RESET)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.RESET) },
                        signals.findByKey(DeviceKey.NEXT)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.NEXT) },
                        signals.findByKey(DeviceKey.PREVIOUS)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.PREVIOUS) },
                        signals.findByKey(DeviceKey.REW)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "REW") },
                        signals.findByKey(DeviceKey.INFO)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.INFO)},
                        signals.findByKey(DeviceKey.EXIT)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.EXIT) },
                        signals.findByKey(DeviceKey.MENU)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.MENU) },
                        signals.findByKey(DeviceKey.HOME)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.HOME) },
                        signals.findByKey(DeviceKey.BACK)
                            ?.let { IconButtonData(keyIdentifier = Name(it.name), iconId = IconType.BACK)},
                        signals.findByKey(DeviceKey.FAR)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "FAR") },
                        signals.findByKey(DeviceKey.NEAR)
                            ?.let { TextButtonData(keyIdentifier = Name(it.name), text = "NEAR") },
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