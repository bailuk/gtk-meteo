package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation

class Days {
    val icons = Box(Orientation.HORIZONTAL, 2)
    private val days = ArrayList<Day>()

    init {
        for (i in 1 .. 6) {
            val day = Day()
            days.add(day)
            icons.packStart(day.box, GTK.FALSE, GTK.TRUE, 2)
        }
        icons.showAll()
    }
}