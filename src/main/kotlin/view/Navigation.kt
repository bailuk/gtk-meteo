package view

import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import config.Layout
import controller.Controller

class Navigation {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.END
        valign = Align.END

        append(Button.newFromIconNameButton(Str("zoom-in-symbolic")).apply {
            onClicked  { Controller.zoomIn() }
            marginEnd = Layout.margin / 2
            marginBottom = Layout.margin
        })

        append(Button.newFromIconNameButton(Str("zoom-out-symbolic")).apply {
            onClicked { Controller.zoomOut() }
            marginEnd = Layout.margin
            marginBottom = Layout.margin
        })
    }
}
