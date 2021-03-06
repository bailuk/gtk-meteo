package view

import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.type.Str

object Util {
    fun setText(label: Label, text: String) {
        val old = label.text
        label.text = Str(text)
        old.destroy()
    }

    fun setMarkup(label: Label, markup: String) {
        val old = label.text
        label.setMarkup(Str(markup))
        old.destroy()
    }
}