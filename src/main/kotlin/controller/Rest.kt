package controller

import config.Files.getJsonFile
import org.mapsforge.core.model.LatLong

// https://api.met.no/weatherapi/locationforecast/2.0/documentation

class Rest {
    private val days = ArrayList<RestClient>()
    private val place  = ArrayList<RestClient>()
    val search = RestClient(getJsonFile("search"),"{\"result\":","}")

    fun setSlots(slot: Slot) {
        while(place.size < slot.count) addSlot()
    }

      fun forEachDay(cb: (Int, RestClient) -> Unit) {
        days.forEachIndexed { index, restClient -> cb(index, restClient)}
    }

    fun forEachPlace(cb: (Int, RestClient) -> Unit) {
        place.forEachIndexed { index, restClient -> cb(index, restClient)}
    }

    fun savePlace(index: Int, center: LatLong, observer: (RestClient)->Unit) {
        withPlace(index) {
            val url = "https://nominatim.openstreetmap.org/reverse"
            it.download(
                "${url}?lat=${center.latitude}&lon=${center.longitude}&format=json",
                observer
            )
        }
    }

    fun saveDays(index: Int, center: LatLong, observer: (RestClient)->Unit) {
         withDays(index) {
             val url = "https://api.met.no/weatherapi/locationforecast/2.0/compact"
             it.download("${url}?lat=${center.latitude}&lon=${center.longitude}", observer)
         }
    }

    fun search(search: String, observer: (RestClient)->Unit) {
        val url = "https://nominatim.openstreetmap.org/search"
        this.search.download("${url}?city=${search}&format=json", observer)
    }

    private fun addSlot() {
        val id = days.size
        println("Add slot: $id")
        days.add(RestClient(getJsonFile("days-$id")))
        place.add(RestClient(getJsonFile("place-$id")))
    }

    private fun withPlace(index: Int, cb: (RestClient) -> Unit) {
        val rest = place.getOrNull(index)
        if (rest is RestClient) {
            cb(rest)
        }
    }

    private fun withDays(index: Int, cb: (RestClient) -> Unit) {
        val rest = days.getOrNull(index)
        if (rest is RestClient) {
            cb(rest)
        }
    }
}
