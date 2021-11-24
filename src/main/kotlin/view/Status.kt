package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.Spinner
import ch.bailu.gtk.type.Str

class Status {
    val box = Box(Orientation.HORIZONTAL, 2)

    init {
        val spinner = Spinner()
        val copyRight = Label(Str("api.met.no / open streetmap"))
        box.packEnd(spinner, GTK.FALSE, GTK.TRUE,2)
        box.packStart(copyRight, GTK.TRUE, GTK.TRUE, 2)
        spinner.start()

    }
}