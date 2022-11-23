package view

import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.pango.EllipsizeMode
import ch.bailu.gtk.type.Str
import controller.Controller
import model.Model

class PlaceLabels {
    val box = Box(Orientation.VERTICAL,0).apply {
        val updatedAt = Label(Str.NULL).apply {
            xalign = 0f
            ellipsize = EllipsizeMode.END
        }
        val location = Label(Str.NULL).apply {
            xalign = 0f
            ellipsize = EllipsizeMode.END
        }

        append(updatedAt)
        append(location)

        Model.observeDays { days, index ->
            if (Controller.isSelectedSlot(index)) {
                updatedAt.setText(days.getUpdatedAtLabel())
                location.setText(days.getLocationLabel())
            }
        }
    }
}
