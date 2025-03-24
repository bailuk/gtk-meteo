package view

import ch.bailu.gtk.gtk.AboutDialog
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.License
import ch.bailu.gtk.gtk.Window
import config.I18n
import config.Strings
import lib.icons.IconMap

object About {
    fun show(window: Window) {
        AboutDialog().apply {
            logo = IconMap.getPaintable("app-icon", 100)
            programName = Strings.appTitle
            version = Strings.version
            website = Strings.website
            copyright = Strings.copyright
            licenseType = License.MIT_X11
            titlebar = createHeaderBar(this)
            transientFor = window
            modal = true
            present()
        }
    }

    private fun createHeaderBar(aboutDialog: AboutDialog): HeaderBar {
        return HeaderBar().apply {
             showTitleButtons = false
             packEnd(Button().apply {
                 setLabel(I18n.getString("close"))
                 onClicked {
                     aboutDialog.close()
                 }
             })
        }
    }
}
