package view

import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.MenuButton
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import config.Strings
import controller.Controller
import lib.extension.ellipsize
import lib.menu.MenuModelBuilder
import model.Model

class MainMenu(window: Window, app: Application) {
    private var places = ArrayList<String>()

    val menuButton = MenuButton().apply {
        iconName = Str("view-more-symbolic")
        Model.observePlace { _, _ ->
            val newPlaces = loadPlaces()
            if (!comparePlaces(places, newPlaces)) {
                places = newPlaces
                menuModel = MenuModelBuilder().apply {
                    places.forEachIndexed { index, element ->
                        label(element) {
                            Controller.selectSlot(index)
                        }
                    }
                    separator("", MenuModelBuilder().label(Strings.info) {
                        About.show(window)
                    })
                }.create(app)
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
