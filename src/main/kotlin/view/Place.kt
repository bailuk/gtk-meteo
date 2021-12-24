package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.pango.EllipsizeMode
import ch.bailu.gtk.type.CPointer
import ch.bailu.gtk.type.Str
import controller.Controller
import model.Model

class Place {
    val box = Box(Orientation.HORIZONTAL,0)

    init {
        val label = Label(Str(CPointer.NULL))
        val button = Button.newFromIconNameButton(Str("mark-location-symbolic"))

        box.append(label)
        box.append(button)

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