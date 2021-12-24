package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import controller.Controller
import model.Config

class Header() {
    val headerBar = HeaderBar()

    init {
        headerBar.showTitleButtons = GTK.TRUE

        val zoomIn = Button.newFromIconNameButton(Str("zoom-in-symbolic"))
        val zoomOut = Button.newFromIconNameButton(Str("zoom-out-symbolic"))
        val update = Button.newFromIconNameButton(Str("view-refresh-symbolic"))

        zoomIn.onClicked  { Controller.zoomIn()      }
        zoomOut.onClicked { Controller.zoomOut()     }
        update.onClicked  { Controller.loadModelFromEndpoint() }

        headerBar.packStart(zoomIn)
        headerBar.packStart(zoomOut)
        headerBar.packEnd(update)
    }
}