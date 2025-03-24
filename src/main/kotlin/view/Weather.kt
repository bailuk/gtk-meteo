package view

import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import model.Model

class Weather {
    val box = Box(Orientation.VERTICAL, 0).apply {
        val place = Place()
        val hours = Hours()
        val days = Days(hours)

        append(place.box)
        append(days.box)
        append(hours.revealer)

        visible = false
        Model.observePlace { _, _ ->
            visible = true
        }
    }

}
