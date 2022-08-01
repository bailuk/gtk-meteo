package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import config.Layout
import config.Strings
import controller.Controller
import lib.ellipsize
import lib.menu.MenuModelBuilder
import model.Model

class Search(private val app: Application) {
    val box = Box(Orientation.HORIZONTAL, 0).apply {
        halign = Align.START
        valign = Align.START
        addCssClass(Strings.linked)
        marginTop = Layout.margin
        marginStart = Layout.margin

        val entry = SearchEntry()
        append(entry.apply {

            onActivate {
                Controller.search(Editable(cast()).text.toString())
            }
        })

        append(Button.newFromIconNameButton(Str("edit-find-symbolic")).apply {
            onClicked {
                Controller.search(Editable(entry.cast()).text.toString())
            }
        })

        append(MenuButton().apply {
            iconName = Str("view-more-symbolic")
            Model.observeSearch {
                this.menuModel = MenuModelBuilder().apply {
                    it.search.forEach { name, latLong ->
                        this.label(name.ellipsize(30)) {
                            Controller.centerMap(latLong)
                        }
                    }
                }.create(app)
            }
        })
    }
}
