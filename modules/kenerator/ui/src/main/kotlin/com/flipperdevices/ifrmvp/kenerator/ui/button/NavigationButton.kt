package com.flipperdevices.ifrmvp.kenerator.ui.button

import com.flipperdevices.infrared.editor.model.InfraredRemote

data class NavigationButton(
    val up: InfraredRemote,
    val down: InfraredRemote,
    val left: InfraredRemote,
    val right: InfraredRemote
)