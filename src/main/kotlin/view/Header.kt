package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import config.Strings

class Header(window: Window) {
    val headerBar = HeaderBar()

    init {
        headerBar.showTitleButtons = GTK.TRUE

        val about = Button.newWithLabelButton(Strings.about)
        about.onClicked   { About.show(window) }

        val add = Button.newFromIconNameButton(Str("list-add-symbolic"))
        val remove = Button.newFromIconNameButton(Str("list-remove-symbolic"))

        remove.visible = GTK.FALSE
        add.onClicked {
            add.visible = GTK.FALSE
            remove.visible = GTK.TRUE
        }
        remove.onClicked {
            remove.visible = GTK.FALSE
            add.visible = GTK.TRUE
        }
        headerBar.packStart(add)
        headerBar.packStart(remove)

        headerBar.packEnd(Button.newFromIconNameButton(Str("open-menu-symbolic")))
        headerBar.packEnd(Button.newFromIconNameButton(Str("go-next-symbolic")))
    }
}
