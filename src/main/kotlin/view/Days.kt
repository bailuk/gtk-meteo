package view

import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import controller.Controller
import model.DaysModel
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

        Model.observeDays { days, index ->
            if (Controller.isSelectedSlot(index)) {
                clearDays()
                updateDays(days)
            }
        }
    }

    private fun clearDays() {
        days.forEach{ it.clear() }
    }

    private fun updateDays(daysModel: DaysModel) {
        try {
             days.forEachIndexed { index, day ->
                 day.update(daysModel.days[index])
            }
        } catch (e: Exception) {}
    }
}
