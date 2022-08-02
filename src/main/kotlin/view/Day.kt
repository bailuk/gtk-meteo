package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.CPointer
import ch.bailu.gtk.type.Str
import lib.IconMap
import model.DayModel

class Day {

    companion object {
        const val ICON_SIZE = 40
    }
    private val box = Box(Orientation.VERTICAL, 0)

    private val day = Label(Str(CPointer.NULL))
    private val icon = Image()
    private val min = Label(Str(CPointer.NULL))
    private val max = Label(Str(CPointer.NULL))

    val button = ToggleButton()


    init {
        button.child = box
        box.append(day)
        box.append(icon)
        icon.hexpand = GTK.TRUE
        icon.setSizeRequest(ICON_SIZE, ICON_SIZE)
        box.append(min)
        box.append(max)
    }

    fun update(day: DayModel) {
        Util.setText(this.day, day.weekDay)
        Util.setText(min,"${day.tempMin}°")
        Util.setText(max, "${day.tempMax}°")
        icon.setFromPixbuf(IconMap.getPixbuf(day.symbol, ICON_SIZE))
    }

    fun clear() {
        Util.setText(day, "")
        Util.setText(min, "")
        Util.setText(max, "")
        icon.clear()
    }

}
