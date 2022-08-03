package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.ScrolledWindow
import config.Layout
import controller.Controller
import model.Model

class Hours {
    companion object {
        const val HOURS = 24
    }
    val scroller = ScrolledWindow()
    private val box = Box(Orientation.HORIZONTAL, Layout.margin)
    private val hours = ArrayList<Hour>()
    private var selected = -1

    init {
        for (i in 0 until HOURS) {
            val hour = Hour()
            hours.add(hour)
            box.append(hour.box)
        }

        scroller.marginTop = Layout.margin
        scroller.marginStart = Layout.margin
        scroller.marginEnd = Layout.margin
        scroller.child = box
        scroller.minContentHeight = 75
        scroller.visible = GTK.FALSE

        Model.observeDays { days, index ->
            if (selected > -1 && Controller.isSelectedSlot(index)) {
                var count = -1
                days.days[selected].forEachSample { sample->
                    if (++count < HOURS) {
                        hours[count].update(sample)
                        hours[count].box.visible = GTK.TRUE
                    }
                }
                while(++count < HOURS) {
                    hours[count].box.visible = GTK.FALSE
                }
            }
        }
    }

    fun selectDay(index: Int) {
        if (selected != index) {
            selected = index
            if (selected < 0) {
                scroller.visible = GTK.FALSE
            } else {
                scroller.visible = GTK.TRUE
                Controller.notifySelectedSlot()
            }
        }
    }
}
