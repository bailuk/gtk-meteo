package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import controller.Controller
import model.Config

class Header() {
    val headerBar = HeaderBar()

    init {
        headerBar.showCloseButton = GTK.TRUE
        headerBar.title = Config.appTitle

        val zoomIn = Button()
        val zoomOut = Button()
        val update = Button()

        zoomIn.onClicked  { Controller.zoomIn()      }
        zoomOut.onClicked { Controller.zoomOut()     }
        update.onClicked  { Controller.loadModelFromEndpoint() }

        zoomIn.image  = Image.newFromIconNameImage(Str("zoom-in-symbolic"), IconSize.BUTTON)
        zoomOut.image = Image.newFromIconNameImage(Str("zoom-out-symbolic"), IconSize.BUTTON)
        update.image  = Image.newFromIconNameImage(Str("view-refresh-symbolic"), IconSize.BUTTON)

        headerBar.packStart(zoomIn)
        headerBar.packStart(zoomOut)
        headerBar.packEnd(update)
    }
}