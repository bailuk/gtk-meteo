package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str

class Search {
    val searchBar = SearchBar()


    init {
        val box = Box(Orientation.HORIZONTAL, 0)
        val entry = SearchEntry()
        val button = Button()
        button.label = Str("Find")
        box.packStart(entry, GTK.FALSE, GTK.TRUE, 2)
        box.packStart(button, GTK.FALSE, GTK.TRUE, 2)
        searchBar.add(box)
        searchBar.connectEntry(entry)
        searchBar.searchMode = GTK.TRUE

    }
}