package view

import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import config.Layout
import config.Strings
import controller.Controller
import ch.bailu.gtk.gtk.Spinner as GtkSpinner

class Spinner {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.CENTER
        valign = Align.CENTER

        addCssClass(Strings.mapCenter)
        append(GtkSpinner().apply {
            setSizeRequest(Layout.CENTER_SQUARE,Layout.CENTER_SQUARE)
            Controller.withSpinner = { cb -> cb(this) }
            marginStart = Layout.MARGIN
            marginTop = Layout.MARGIN
        })
    }
}
