package model

import parser.JsonMap
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class DayModel(dateTime: ZonedDateTime) {
    private val date: LocalDate = dateTime.toLocalDate()
    private var lastUpdate = dateTime.toLocalTime()

    var tempMin = 10000.0
    var tempMax = -10000.0

    var symbol = ""
    val weekDay: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

    private val samples = ArrayList<Sample>()

    fun isSameDay(dateTime: ZonedDateTime): Boolean {
        return date == dateTime.toLocalDate()
    }

    fun update(dateTime: ZonedDateTime, data: JsonMap) {
        var airTemperature = 0.0
        var sampleSymbol = ""

        data.map("instant") {
            it.map("details") {
                it.number("air_temperature") { temp ->
                    airTemperature = temp
                    tempMin = Math.min(tempMin, temp)
                    tempMax = Math.max(tempMax, temp)
                }
            }
        }

        data.map("next_12_hours") { next12 ->
            sampleSymbol = getSymbol(next12)
            if (symbol == "" || lastUpdate.hour < 7) {
                symbol = sampleSymbol
            }
        }

        data.map("next_6_hours") { next6 ->
            sampleSymbol = getSymbol(next6)
            if (symbol == "") {
                symbol = sampleSymbol
            }
        }

        data.map("next_1_hours") { next1 ->
            sampleSymbol = getSymbol(next1)
            if (symbol == "") {
                symbol = sampleSymbol
            }
        }

        samples.add(Sample(dateTime, airTemperature, sampleSymbol))
        lastUpdate = dateTime.toLocalTime()
    }


    private fun getSymbol(map: JsonMap): String {
        var result = ""
        map.map("summary") { jsonMap ->
            jsonMap.string("symbol_code") { symbolCode ->
                result = symbolCode
            }
        }
        return result
    }

    fun forEachSample(cb: (Sample)->Unit) {
        samples.forEach(cb)
    }

    override fun toString(): String {
        return "$weekDay $symbol $tempMin | $tempMax"
    }
}
