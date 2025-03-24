package view

import ch.bailu.gtk.gtk.Label
import config.I18n
import model.Model

class Intro {
    val label = Label(I18n.getString("intro")).apply {
        addCssClass("intro")
        wrap = true

        Model.observePlace { _, _ ->
            visible = false
        }
    }
}
