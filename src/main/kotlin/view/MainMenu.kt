package view

import ch.bailu.gtk.gio.Menu
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.MenuButton
import ch.bailu.gtk.lib.handler.action.ActionHandler
import ch.bailu.gtk.type.Str
import config.Keys
import config.Strings
import controller.Controller
import lib.extension.ellipsize
import model.Model

class MainMenu(app: Application) {
    private var places = ArrayList<String>()

    val menuButton = MenuButton().apply {
        iconName = Str("view-more-symbolic")
        Model.observePlace { _, _ ->
            val newPlaces = loadPlaces()
            if (!comparePlaces(places, newPlaces)) {
                places = newPlaces

                menuModel = Menu().apply {

                    appendSection(Str.NULL, Menu().apply {
                        places.forEachIndexed { index, element ->
                            append(element, "app.${Keys.SLOT}$index")
                            ActionHandler.get(app, "${Keys.SLOT}$index").disconnectSignals()
                            ActionHandler.get(app, "${Keys.SLOT}$index").onActivate { ->
                                Controller.selectSlot(index)
                            }
                        }
                    })
                    appendSection(Str.NULL, Menu().apply {
                        append(Strings.AUTO_CENTER, "app.${Keys.AUTO_CYCLE}")
                        append(Strings.INFO, "app.${Keys.ABOUT}")
                    })
                }
            }
        }
    }

    private fun loadPlaces(): ArrayList<String> {
        val places = ArrayList<String>()
        Model.forEachPlace { _, place ->
            places.add(place.toString().ellipsize(30))
        }
        return places
    }

    private fun comparePlaces(listA: ArrayList<String>, listB: ArrayList<String>): Boolean {
        if (listA.size != listB.size) {
            return false
        } else {
            listA.forEachIndexed { index, element ->
                if (element != listB[index]) {
                    return false
                }
            }
        }
        return true
    }
}
