package view

import ch.bailu.gtk.gtk.*
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
        box.append(entry)
        box.append(button)

        entry.onActivate {
            Controller.search(Editable(entry.cast()).text.toString())
        }

        button.onClicked {
            Controller.search(Editable(entry.cast()).text.toString())
        }
    }
}