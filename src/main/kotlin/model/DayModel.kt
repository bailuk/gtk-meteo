package model

import parser.JsonMap
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.*

class DayModel(dateTime: ZonedDateTime) {
    val date = dateTime.toLocalDate()
    private var lastUpdate = dateTime.toLocalTime()

    var tempMin = 10000.0
    var tempMax = -10000.0

    var symbol = ""
    val weekDay = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)


    fun isSameDay(dateTime: ZonedDateTime): Boolean {
        return date.equals(dateTime.toLocalDate())
    }

    fun update(dateTime: ZonedDateTime, data: JsonMap) {
        data.map("instant") {
            it.map("details") {
                it.number("air_temperature") {
                    tempMin = Math.min(tempMin, it)
                    tempMax = Math.max(tempMax, it)
                }
            }
        }

        if (symbol == "" || lastUpdate.hour < 7) {
            data.map("next_12_hours") {
                updateSymbol(it)
            }
            if (symbol == "") {
                data.map("next_6_hours") {
                    updateSymbol(it)
                }
                if (symbol == "") {
                    data.map("next_1_hours") {
                        updateSymbol(it)
                    }
                }
            }
        }


       lastUpdate = dateTime.toLocalTime()
    }


    private fun updateSymbol(map: JsonMap) {
        map.map("summary") {
            it.string("symbol_code") {
                symbol = it
            }
        }
    }

    override fun toString() : String {
        return "$weekDay $symbol $tempMin | $tempMax"
    }
}
