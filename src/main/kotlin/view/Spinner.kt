package view

import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import config.Layout
import controller.Controller
import ch.bailu.gtk.gtk.Spinner as GtkSpinner

class Spinner {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.CENTER
        valign = Align.CENTER

        addCssClass(Str("map-center"))
        append(GtkSpinner().apply {
            setSizeRequest(50,50)
            Controller.spinner = this
            marginStart = Layout.margin
            marginTop = Layout.margin
        })
    }
}
