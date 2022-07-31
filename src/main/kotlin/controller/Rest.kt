package controller

import Config
import org.mapsforge.core.model.LatLong

// https://api.met.no/weatherapi/locationforecast/2.0/documentation

object Rest {
    var days   = RestClient(Config.getJsonFile("days"))
    var place  = RestClient(Config.getJsonFile("place"))
    var search = RestClient(Config.getJsonFile("search"),"{\"result\":","}")

    fun savePlace(center: LatLong, observer: (RestClient)->Unit) {
        val url = "https://nominatim.openstreetmap.org/reverse"
        place.download("${url}?lat=${center.latitude}&lon=${center.longitude}&format=json", observer)
    }

     fun saveDays(center: LatLong, observer: (RestClient)->Unit) {
        val url = "https://api.met.no/weatherapi/locationforecast/2.0/compact"
        days.download("${url}?lat=${center.latitude}&lon=${center.longitude}", observer)
    }

    fun search(search: String, observer: (RestClient)->Unit) {
        val url = "https://nominatim.openstreetmap.org/search"
        this.search.download("${url}?city=${search}&format=json", observer)
    }
}
