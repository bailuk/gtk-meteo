package view

import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import config.Strings
import controller.Controller

class PlaceControl {
    val box = Box(Orientation.HORIZONTAL,0).apply {
        addCssClass(Strings.linked)
        append(Button.newFromIconNameButton(Str("view-refresh-symbolic")).apply {
            onClicked {
                Controller.updateSelectedSlot()
            }
        })
        append(Button.newFromIconNameButton(Str("find-location-symbolic")).apply {
            onClicked {
                Controller.showPlace()
            }
        })
    }
}
