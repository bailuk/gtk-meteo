package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import config.Strings
import lib.icons.IconMap
import model.DayModel

class Day {

    companion object {
        const val ICON_SIZE = 40
    }
    private val box = Box(Orientation.VERTICAL, 0)

    private val day = Label(Str.NULL)
    private val icon = Image()
    private val min = Label(Str.NULL)
    private val max = Label(Str.NULL)

    val button = ToggleButton()

    init {
        button.child = box
        box.append(day)
        box.append(icon)
        icon.hexpand = true
        icon.setSizeRequest(ICON_SIZE, ICON_SIZE)
        box.append(min)
        box.append(max)
    }

    fun update(day: DayModel) {
        this.day.setText(day.weekDay)
        min.setText("${day.tempMin}°")
        max.setText("${day.tempMax}°")
        icon.setFromPixbuf(IconMap.getPixbuf(day.symbol, ICON_SIZE))
    }

    fun clear() {
        day.text = Strings.empty
        min.text = Strings.empty
        max.text = Strings.empty
        icon.clear()
    }
}
