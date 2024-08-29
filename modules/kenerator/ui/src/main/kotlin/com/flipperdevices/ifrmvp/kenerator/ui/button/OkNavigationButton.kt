package com.flipperdevices.ifrmvp.kenerator.ui.button

import com.flipperdevices.infrared.editor.model.InfraredRemote

data class OkNavigationButton(
    val up: InfraredRemote,
    val down: InfraredRemote,
    val left: InfraredRemote,
    val right: InfraredRemote,
    val ok: InfraredRemote
)