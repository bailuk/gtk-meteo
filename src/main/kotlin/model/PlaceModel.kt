package model

import lib.json.JsonMap

class PlaceModel {
    var city = ""
        private set

    var country = ""
        private set

    var markup = ""
        private set

    private var nonMarkup = ""

    fun parse(place: JsonMap) {
        clearNames()
        setNames(place)
        setMarkup()
        setNonMarkup()
    }

    private fun setNonMarkup() {
        nonMarkup = getFullText()
    }

    private fun setMarkup() {
        markup = getFullText("<b>", "</b>")
    }

    private fun getFullText(tagO: String = "", tagC: String = ""): String {
        return if (city.isEmpty()) {
            country
        } else if (country.isEmpty()) {
            city
        } else {
            "${tagO}${city}${tagC} $country"
        }
    }

    private fun clearNames() {
        city = ""
        markup = ""
        country = ""
    }

    private fun setNames(place: JsonMap) {
        place.map("address") {

            it.string("road") {
                city = it
            }

            it.string("province") {
                country = it
                city = it
            }

            it.string("county") {
                country = it
            }

            it.string("state") {
                country = it
            }

            it.string("country") {
                country = it
            }

            it.string("village") {
                city = it
            }

            it.string("town") {
                city = it
            }

            it.string("city") {
                city = it
            }
        }
    }

    override fun toString(): String {
        return nonMarkup
    }
}
