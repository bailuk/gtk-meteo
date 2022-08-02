package model

import org.mapsforge.core.model.LatLong
import parser.JsonMap
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class DaysModel {

    val days = ArrayList<DayModel>()
    private val coords = ArrayList<Double>()
    private var updatedAt = ZonedDateTime.now()

    fun getLatLong() : LatLong {
        return LatLong(getLatitude(), getLongitude())
    }

    private fun getLatitude(): Double {
        return if (coords.size > 1) coords[1] else 0.0
    }

    private fun getLongitude(): Double {
        return if (coords.size > 0) coords[0] else 0.0
    }

    private fun getAltitude(): Double {
        return if (coords.size > 2) coords[2] else 0.0
    }

    fun getUpdatedAtLabel(): String {
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        return updatedAt.toLocalDateTime().format(dateTimeFormatter)
    }

    fun getLocationLabel(): String {
        return "${getLatitude().format(3)}, ${getLongitude().format(3)}, ${getAltitude().format(0)}m"
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
            it.map("meta") { meta->
                meta.string("updated_at") { date ->
                    updatedAt = ZonedDateTime.parse(date)
                }
            }
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
