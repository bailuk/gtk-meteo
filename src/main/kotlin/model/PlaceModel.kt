package model

import parser.JsonMap

class PlaceModel {
    var city = ""
        private set

    var country = ""
        private set

    var markup = ""
        private set


    fun parse(place: JsonMap) {
        clearNames()
        setNames(place)
        setMarkup()
    }

    private fun setMarkup() {
        if (city == "") {
            markup = country
        } else if (country == "") {
            markup = city
        } else {
            markup = "<b>${city}</b> ${country}"
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
}
