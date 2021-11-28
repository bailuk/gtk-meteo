package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.pango.EllipsizeMode
import ch.bailu.gtk.type.Str
import controller.Controller
import model.Model

class Place {
    val box = Box(Orientation.HORIZONTAL,0)

    init {
        val label = Label(null)
        val button = Button()

        button.image = Image.newFromIconNameImage(Str("mark-location-symbolic"), IconSize.BUTTON)

        box.packStart(label, GTK.TRUE, GTK.TRUE,2)
        box.packEnd(button, GTK.FALSE, GTK.TRUE, 2)

        label.useMarkup = GTK.TRUE
        label.xalign = 0f
        label.ellipsize = EllipsizeMode.END


        button.onClicked {
            Controller.showPlace()
        }

        Model.observeDays {
            label.tooltipText = Str(it.days.getLabel())
        }

        Model.observePlace {
            Util.setMarkup(label, it.place.markup)
        }
    }

}