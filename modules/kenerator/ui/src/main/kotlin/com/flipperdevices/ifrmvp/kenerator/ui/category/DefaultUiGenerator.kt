package com.flipperdevices.ifrmvp.kenerator.ui.category

import com.flipperdevices.ifrmvp.kenerator.ui.category.tv.TvMap
import com.flipperdevices.ifrmvp.kenerator.ui.core.BrandMap
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findButtonsData
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findChannelButton
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findNavigationRemote
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findOkNavigationRemote
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.findVolumeButton
import com.flipperdevices.ifrmvp.kenerator.ui.util.RemotesListExt.identifier
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier.Name
import com.flipperdevices.ifrmvp.model.PageLayout
import com.flipperdevices.ifrmvp.model.PagesLayout
import com.flipperdevices.ifrmvp.model.buttondata.ChannelButtonData
import com.flipperdevices.ifrmvp.model.buttondata.NavigationButtonData
import com.flipperdevices.ifrmvp.model.buttondata.OkNavigationButtonData
import com.flipperdevices.ifrmvp.model.buttondata.VolumeButtonData
import com.flipperdevices.infrared.editor.model.InfraredRemote

class DefaultUiGenerator(private val map: BrandMap) {

    fun generate(signals: List<InfraredRemote>): PagesLayout {
        return PagesLayout(
            pages = listOf(
                PageLayout(
                    buttons = buildList {
                        signals.findButtonsData().run(::addAll)
                        signals.findVolumeButton()
                            ?.let { VolumeButtonData(it.add.identifier, it.reduce.identifier) }
                            ?.run(::add)
                        signals.findChannelButton()
                            ?.let { ChannelButtonData(it.next.identifier, it.prev.identifier) }
                            ?.run(::add)
                        signals.findOkNavigationRemote()
                            ?.let {
                                OkNavigationButtonData(
                                    upKeyIdentifier = it.up.identifier,
                                    leftKeyIdentifier = it.left.identifier,
                                    rightKeyIdentifier = it.right.identifier,
                                    downKeyIdentifier = it.down.identifier,
                                    okKeyIdentifier = it.ok.identifier
                                )
                            }?.run(::add)
                        signals.findNavigationRemote()
                            ?.let {
                                NavigationButtonData(
                                    upKeyIdentifier = it.up.identifier,
                                    leftKeyIdentifier = it.left.identifier,
                                    rightKeyIdentifier = it.right.identifier,
                                    downKeyIdentifier = it.down.identifier,
                                )
                            }?.run(::add)
                    }.let(map::transform)
                )
            )
        )
    }
}