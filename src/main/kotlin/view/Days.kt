package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import config.Layout
import config.Strings
import controller.Controller
import model.DaysModel
import model.Model

class Days(private val dayDetail: Hours) {
    companion object {
        const val DAYS = 5
    }
    val icons = Box(Orientation.HORIZONTAL, 0)
    private val days = ArrayList<Day>()

    init {
        for (i in 0 until DAYS) {
            val day = Day()
            day.button.onToggled {
                if (GTK.IS(day.button.active)) {
                    selectDay(i)
                } else {
                    unselectDay()
                }
            }
            days.add(day)
            icons.append(day.button)
        }
        icons.show()
        icons.marginStart = Layout.margin
        icons.marginEnd = Layout.margin
        icons.addCssClass(Strings.linked)

        Model.observeDays { days, index ->
            if (Controller.isSelectedSlot(index)) {
                clearDays()
                updateDays(days)
            }
        }
    }

    private fun unselectDay() {
        dayDetail.selectDay(getSelectedDay())
    }

    private fun selectDay(index: Int) {
        days.forEachIndexed { i, day ->
            if (i != index) day.button.active = GTK.FALSE
        }
        dayDetail.selectDay(getSelectedDay())
    }

    private fun getSelectedDay(): Int {
        days.forEachIndexed { index, day ->
            if (GTK.IS(day.button.active)) {
                return index
            }
        }
        return -1
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
