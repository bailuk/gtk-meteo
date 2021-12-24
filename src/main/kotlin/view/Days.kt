package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import model.DayModel
import model.Model

class Days {
    val icons = Box(Orientation.HORIZONTAL, 2)
    private val days = ArrayList<Day>()

    init {
        for (i in 1 .. 6) {
            val day = Day()
            days.add(day)
            icons.append(day.box)
        }
        icons.show()

        Model.observeDays {
            clearDays()
            updateDays()
        }
    }

    private fun clearDays() {
        days.forEach{ it.clear() }
    }

    private fun updateDays() {
        try {
            var index = 0
            days.forEach {
                it.update(Model.days.days[index])
                index++
            }
        } catch (e: Exception) {}
    }
}