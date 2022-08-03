package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.pango.EllipsizeMode
import ch.bailu.gtk.type.Str
import config.Layout
import controller.Controller
import lib.extension.setMarkup
import model.Model

class Place {
    val box = Box(Orientation.VERTICAL,0).apply {
        marginStart = Layout.margin
        marginEnd = Layout.margin
        marginBottom = Layout.margin
        marginTop = Layout.margin

        val name = Label(Str.NULL).apply {
            useMarkup = GTK.TRUE
            xalign = 0f
            ellipsize = EllipsizeMode.END
        }

        append(name)
        append(Box(Orientation.HORIZONTAL, 0).apply {
            append(PlaceLabels().box)
            append(Label(Str.NULL).apply { hexpand =GTK.TRUE})
            append(PlaceControl().box)
        })

        Model.observePlace { place, index ->
            if (Controller.isSelectedSlot(index)) {
                name.setMarkup(place.markup)
            }
        }
    }
}
