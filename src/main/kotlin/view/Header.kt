package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import config.Strings
import controller.Controller

class Header(window: Window) {
    val headerBar = HeaderBar()

    private val add = Button.newFromIconNameButton(Str("list-add-symbolic"))
    private val remove = Button.newFromIconNameButton(Str("list-remove-symbolic"))

    init {
        headerBar.showTitleButtons = GTK.TRUE

        val about = Button.newWithLabelButton(Strings.about)
        about.onClicked   { About.show(window) }


        add.onClicked {
            updateBookmarkButtons()
        }

        remove.onClicked {
            updateBookmarkButtons()
        }

        headerBar.packStart(add)
        headerBar.packStart(remove)

        headerBar.packEnd(Button.newFromIconNameButton(Str("open-menu-symbolic")))
        headerBar.packEnd(Button.newFromIconNameButton(Str("go-next-symbolic")).apply {
            onClicked {
                Controller.selectNextSlot()
            }
        })

        updateBookmarkButtons()
    }

    private fun updateBookmarkButtons() {
        remove.visible = GTK.TRUE
        add.visible = GTK.FALSE
    }
}
