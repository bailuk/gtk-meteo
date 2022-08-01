package view

import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import config.Layout
import config.Strings
import controller.Controller

class Navigation {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.END
        valign = Align.END
        marginEnd = Layout.margin
        marginBottom = Layout.margin
        addCssClass(Strings.linked)

        append(Button.newFromIconNameButton(Str("zoom-in-symbolic")).apply {
            onClicked  { Controller.zoomIn() }
        })

        append(Button.newFromIconNameButton(Str("zoom-out-symbolic")).apply {
            onClicked { Controller.zoomOut() }
        })
    }
}
