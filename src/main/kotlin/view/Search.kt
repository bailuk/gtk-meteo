package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.SearchEntry
import ch.bailu.gtk.type.Str
import controller.Controller

class Search {
    val box = Box(Orientation.HORIZONTAL, 0)


    init {
        val entry = SearchEntry()
        val button = Button()

        button.label = Str("Find")
        button.marginBottom = 5
        button.marginTop = 5
        entry.marginBottom = 5
        entry.marginTop = 5
        box.packStart(entry, GTK.TRUE, GTK.TRUE, 5)
        box.packStart(button, GTK.FALSE, GTK.TRUE, 5)

        entry.onActivate {
            Controller.search(entry.text.toString())
        }

        button.onClicked {
            Controller.search(entry.text.toString())
        }
    }
}