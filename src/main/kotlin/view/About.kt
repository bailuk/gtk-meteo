package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import lib.IconMap
import Config

object About {
    fun show(window: Window) {
        AboutDialog().apply {
            logo = IconMap.getPaintable("app-icon", 100)
            programName = Config.appTitle
            version = Config.version
            website = Config.website
            copyright = Config.copyright
            licenseType = License.MIT_X11
            titlebar = createHeaderBar(this)
            transientFor = window
            modal = GTK.TRUE
            show()
        }
    }

    private fun createHeaderBar(aboutDialog: AboutDialog): HeaderBar {
        return HeaderBar().apply {
             showTitleButtons = GTK.FALSE
             packEnd(Button().apply {
                 label = Strings.close
                 onClicked {
                     aboutDialog.close()
                 }
             })
        }
    }
}
