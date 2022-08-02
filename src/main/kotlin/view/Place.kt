package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.pango.EllipsizeMode
import ch.bailu.gtk.type.CPointer
import ch.bailu.gtk.type.Str
import config.Layout
import controller.Controller
import model.Model

class Place {
    val box = Box(Orientation.HORIZONTAL,0)

    init {
        val label = Label(Str(CPointer.NULL))

        box.append(label)
        box.append(Label(Str.NULL).apply { hexpand =GTK.TRUE})
        box.append(Button.newFromIconNameButton(Str("view-refresh-symbolic")).apply {
            marginTop = Layout.margin
            marginBottom = Layout.margin
            marginEnd = Layout.margin / 2
            onClicked {
                Controller.updateSelectedSlot()
            }
        })
        box.append(Button.newFromIconNameButton(Str("find-location-symbolic")).apply {
            marginEnd = Layout.margin
            marginTop = Layout.margin
            marginBottom = Layout.margin
            onClicked {
                Controller.showPlace()
            }
        })

        label.useMarkup = GTK.TRUE
        label.xalign = 0f
        label.ellipsize = EllipsizeMode.END
        label.marginStart = Layout.margin
        label.marginEnd = Layout.margin

        Model.observeDays { days, index ->
            if (Controller.isSelectedSlot(index)) {
                label.tooltipText = Str(days.getLabel())
            }
        }

        Model.observePlace { place, index ->
            if (Controller.isSelectedSlot(index)) {
                Util.setMarkup(label, place.markup)
            }
        }
    }

}
