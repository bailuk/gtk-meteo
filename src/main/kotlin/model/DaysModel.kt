package model

import org.mapsforge.core.model.LatLong
import parser.JsonMap
import java.time.ZonedDateTime
import java.util.ArrayList

class DaysModel {

    val days = ArrayList<DayModel>()
    private val coords = ArrayList<Double>()

    fun getLatLong() : LatLong {
        return LatLong(getLatitude(), getLongitude())
    }

    fun getLatitude(): Double {
        return if (coords.size > 1) coords[1] else 0.0
    }

    fun getLongitude(): Double {
        return if (coords.size > 0) coords[0] else 0.0
    }

    private fun getAltitude(): Double {
        return if (coords.size > 2) coords[2] else 0.0
    }

    fun getLabel(): String {
        return "${getLatitude().format(2)}, ${getLongitude().format(2)}, ${getAltitude().format(0)}m"
    }


    fun parse(map: JsonMap) {
        days.clear()
        coords.clear()
        map.map("geometry") { geo ->
            geo.number("coordinates") { coord ->
                coords.add(coord)
            }
        }

        map.map("properties") {
            it.map("timeseries") { sample ->
                sample.string("time") { time ->
                    val date = ZonedDateTime.parse(time)

                    if (days.size == 0 || !days.last().isSameDay(date)) {
                        days.add(DayModel(date))
                    }

                    sample.map("data") { data ->
                        days.last().update(date, data)
                    }
                }

            }
        }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
