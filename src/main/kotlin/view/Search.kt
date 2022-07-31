package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import config.Layout
import controller.Controller

class Search {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.START
        valign = Align.START

        val entry = SearchEntry()
        append(entry.apply {
            marginTop = Layout.margin
            marginStart = Layout.margin
            marginEnd = Layout.margin / 2

            onActivate {
                Controller.search(Editable(cast()).text.toString())
            }
        })
        append(Button.newFromIconNameButton(Str("edit-find-symbolic")).apply {
            marginTop = Layout.margin
            marginEnd = Layout.margin / 2
            onClicked {
                Controller.search(Editable(entry.cast()).text.toString())
            }
        })
        append(Button.newFromIconNameButton(Str("view-more-symbolic")).apply {
            marginTop = Layout.margin
        })

    }
}
