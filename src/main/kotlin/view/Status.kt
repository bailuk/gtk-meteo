package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.Spinner
import ch.bailu.gtk.pango.EllipsizeMode
import ch.bailu.gtk.type.Str
import controller.Controller
import model.Config

class Status {
    val box = Box(Orientation.HORIZONTAL, 0)

    private val copy = Box(Orientation.VERTICAL, 0)
    init {
        val spinner = Spinner()
        addLabel(Config.license)
        addLabel(Config.metLicense)
        addLabel(Config.osmLicense)

        box.append(spinner)
        box.append(copy)
        Controller.spinner = spinner
    }

    private fun addLabel(text: Str) {
        val label = Label(text)

        copy.append(label)
        label.useMarkup = GTK.TRUE
        label.useMarkup = GTK.TRUE
        label.xalign = 0f
        label.ellipsize = EllipsizeMode.END
    }
}