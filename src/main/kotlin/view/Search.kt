package view

import ch.bailu.gtk.gtk.*
import config.Layout
import config.Strings
import controller.Controller
import lib.extension.ellipsize
import lib.menu.Actions
import lib.menu.MenuModelBuilder
import model.Model

class Search(private val actions: Actions) {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.START
        valign = Align.START
        addCssClass(Strings.linked)
        marginTop = Layout.margin
        marginStart = Layout.margin

        val entry = Entry()
        append(entry.apply {

            onActivate {
                Controller.search(Editable(cast()).text.toString())
            }
        })

        append(Button.newFromIconNameButton("edit-find-symbolic").apply {
            onClicked {
                Controller.search(Editable(entry.cast()).text.toString())
            }
        })

        append(MenuButton().apply {
            setIconName("view-more-symbolic")
            Model.observeSearch {
                menuModel = MenuModelBuilder().apply {
                    it.forEach { name, latLong ->
                        label(name.ellipsize(30)) {
                            Controller.centerMap(latLong)
                        }
                    }
                }.create(actions)
            }
        })
    }
}
