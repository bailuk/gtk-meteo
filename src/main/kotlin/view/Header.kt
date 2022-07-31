package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import controller.Controller

class Header(window: Window) {
    val headerBar = HeaderBar()

    init {
        headerBar.showTitleButtons = GTK.TRUE

        val zoomIn = Button.newFromIconNameButton(Str("zoom-in-symbolic"))
        val zoomOut = Button.newFromIconNameButton(Str("zoom-out-symbolic"))
        val about = Button.newWithLabelButton(Strings.about)
        val update = Button.newFromIconNameButton(Str("view-refresh-symbolic"))

        zoomIn.onClicked  { Controller.zoomIn()      }
        zoomOut.onClicked { Controller.zoomOut()     }
        update.onClicked  { Controller.loadModelFromEndpoint() }
        about.onClicked   { About.show(window) }

        headerBar.packStart(zoomIn)
        headerBar.packStart(zoomOut)
        headerBar.packStart(Spinner().apply {
            Controller.spinner = this
        })
        headerBar.packEnd(about)
        headerBar.packEnd(update)
    }
}
