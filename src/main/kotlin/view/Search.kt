package view

import ch.bailu.gtk.gio.Menu
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.lib.handler.action.ActionHandler
import config.Layout
import config.Strings
import controller.Controller
import lib.extension.ellipsize
import model.Model

class Search(private val app: Application) {
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
                menuModel = Menu().apply {
                    var i = 0
                    it.forEach { name, latLong ->
                        append(name.ellipsize(30), "app.search-result$i")
                        i++
                        ActionHandler.get(app, "search-result$i").disconnectSignals()
                        ActionHandler.get(app, "search-result$i").onActivate { ->
                            Controller.centerMap(latLong)
                        }
                    }
                }
            }
        })
    }
}
