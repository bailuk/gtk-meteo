package view

import ch.bailu.gtk.adw.AboutDialog
import ch.bailu.gtk.gtk.License
import ch.bailu.gtk.gtk.Window
import config.Strings

object About {
    fun show(window: Window) {
        AboutDialog().apply {
            applicationName = Strings.appTitle
            applicationIcon = Strings.appID
            version = Strings.version
            website = Strings.website
            copyright = Strings.copyright
            licenseType = License.MIT_X11
            present(window)
        }
    }
}
