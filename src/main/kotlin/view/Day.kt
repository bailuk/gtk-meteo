package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Image
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.CPointer
import ch.bailu.gtk.type.Str
import lib.IconMap
import model.DayModel

class Day {

    val box = Box(Orientation.VERTICAL, 0)

    private val day = Label(Str(CPointer.NULL))
    private val icon = Image()
    private val min = Label(Str(CPointer.NULL))
    private val max = Label(Str(CPointer.NULL))


    init {
        box.append(day)
        box.append(icon)
        icon.hexpand = GTK.TRUE
        icon.setSizeRequest(50,50)
        box.append(min)
        box.append(max)
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
