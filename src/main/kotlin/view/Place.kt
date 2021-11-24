package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str

class Place {
    val box = Box(Orientation.HORIZONTAL,0)



    init {
        val label = Label(Str("<b>Truttikon, Switzerland</b>"))
        val button = Button()
        button.image = Image.newFromIconNameImage(Str("mark-location-symbolic"), IconSize.BUTTON)
        box.packStart(label, GTK.TRUE, GTK.TRUE,2)
        box.packEnd(button, GTK.FALSE, GTK.TRUE, 2)
        label.useMarkup = GTK.TRUE
        label.xalign = 0f
    }

}