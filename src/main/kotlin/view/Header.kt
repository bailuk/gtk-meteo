package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str

class Header(title: String) {
    val headerBar = HeaderBar()


    init {
        headerBar.showCloseButton = GTK.TRUE
        headerBar.title = Str(title)


        val zoomIn = Button()
        val zoomOut = Button()
        val update = Button()

        zoomIn.image = Image.newFromIconNameImage(Str("zoom-in-symbolic"), IconSize.BUTTON)
        zoomOut.image = Image.newFromIconNameImage(Str("zoom-out-symbolic"), IconSize.BUTTON)
        update.image = Image.newFromIconNameImage(Str("view-refresh-symbolic"), IconSize.BUTTON)


        headerBar.packStart(zoomIn)
        headerBar.packStart(zoomOut)
        headerBar.packEnd(update)
    }
}