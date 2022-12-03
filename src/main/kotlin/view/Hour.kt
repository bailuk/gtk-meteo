package view

import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Image
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.type.Str
import lib.icons.IconMap
import model.Sample

class Hour {
    companion object {
        const val ICON_SIZE = 25
    }
    val box = Box(Orientation.VERTICAL, 0)

    private val time = Label(Str.NULL)
    private val icon = Image()
    private val temp = Label(Str.NULL)

    init {
        box.append(time)
        box.append(icon)
        icon.hexpand = true
        icon.setSizeRequest(ICON_SIZE, ICON_SIZE)
        box.append(temp)
    }

    fun update(sample: Sample) {
        time.setText(sample.dateTime.toLocalTime().hour.toString())
        temp.setText("${sample.airTemperature}°")
        icon.setFromPixbuf(IconMap.getPixbuf(sample.symbol, ICON_SIZE))
    }
}
