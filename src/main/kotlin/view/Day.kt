package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Image
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import model.DayModel

class Day {

    val box = Box(Orientation.VERTICAL, 0)

    private val day = Label(null)
    private val icon = Image()
    private val min = Label(null)
    private val max = Label(null)


    init {
        box.packStart(day, GTK.FALSE, GTK.TRUE, 0)
        box.packStart(icon, GTK.FALSE, GTK.TRUE, 0)
        box.packStart(min, GTK.FALSE, GTK.TRUE, 0)
        box.packStart(max, GTK.FALSE, GTK.TRUE, 0)
    }

    fun update(day: DayModel) {
        Util.setText(this.day, day.weekDay)
        Util.setText(min,"${day.tempMin}°")
        Util.setText(max, "${day.tempMax}°")
        icon.setFromPixbuf(IconMap.getPixbuf(day.symbol, 50))
    }

    fun clear() {
        Util.setText(day, "")
        Util.setText(min, "")
        Util.setText(max, "")
        icon.clear()
    }

}