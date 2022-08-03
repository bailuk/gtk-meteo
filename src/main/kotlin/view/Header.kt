package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import config.Strings
import controller.Controller
import lib.ellipsize
import lib.menu.MenuModelBuilder
import model.Model

class Header(window: Window, app: Application) {
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

        val box = Box(Orientation.HORIZONTAL, 0)
        box.addCssClass(Strings.linked)
        box.append(Button.newFromIconNameButton(Str("go-next-symbolic")).apply {
            onClicked {
                Controller.selectNextSlot()
            }
        })
        box.append(MenuButton().apply {
            iconName = Str("view-more-symbolic")
            Model.observePlace { _, _ ->
                menuModel = MenuModelBuilder().apply {
                    Model.forEachPlace { index, place ->
                        label(place.toString().ellipsize(30)) {
                            Controller.selectSlot(index)
                        }
                    }
                    separator("", MenuModelBuilder().label("Info…") {
                        About.show(window)
                    })
                }.create(app)
            }
        })

        headerBar.packEnd(box)
        updateBookmarkButtons()
    }

    private fun updateBookmarkButtons() {
        remove.visible = GTK.TRUE
        add.visible = GTK.FALSE
    }
}
