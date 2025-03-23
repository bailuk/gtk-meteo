package view

import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import config.Layout
import controller.Controller

class Select {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.END
        valign = Align.START
        marginEnd = Layout.MARGIN
        marginTop = Layout.MARGIN

        append(Button.newFromIconNameButton(Str("go-up-symbolic")).apply {
            onClicked  { Controller.addMapCenterToModelAndLoad() }
        })
    }
}
