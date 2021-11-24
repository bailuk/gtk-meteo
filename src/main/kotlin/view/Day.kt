package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Image
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str

class Day {
    val box = Box(Orientation.VERTICAL, 0)

    private val day = Label(Str("Mo"))
    private val icon = Image()
    private val temp = Label(Str("1° | 5°"))


    init {
        box.packStart(day, GTK.FALSE, GTK.TRUE, 0)
        icon.setFromPixbuf(IconMap.getPixbuf("cloudy", 50))
        box.packStart(icon, GTK.FALSE, GTK.TRUE, 0)
        box.packStart(temp, GTK.FALSE, GTK.TRUE, 0)
    }
}