package model

import org.mapsforge.core.model.LatLong
import parser.JsonMap

object Model {
    private const val SAME_PLACE = 2500

    private val dayObservers = ArrayList<(DaysModel, Int)->Unit>()
    private val placeObservers = ArrayList<(PlaceModel, Int)->Unit>()
    private val searchObservers = ArrayList<(SearchModel)->Unit>()

    private val days = ArrayList<DaysModel>()
    private val place = ArrayList<PlaceModel>()
    val search = SearchModel()

    fun withDays(index: Int, cb: (DaysModel) -> Unit) {
        val days = days.getOrNull(index)
        if (days is DaysModel) cb(days)
    }

    private fun withPlace(index: Int, cb: (PlaceModel) -> Unit) {
        val place = place.getOrNull(index)
        if (place is PlaceModel) cb(place)
    }

    fun observePlace(observer : (PlaceModel, Int) -> Unit) {
        placeObservers.add(observer)
    }

    fun observeDays(observer : (DaysModel, Int) -> Unit) {
        dayObservers.add(observer)
    }

    fun observeSearch(observer: (SearchModel) -> Unit) {
        searchObservers.add(observer)
    }

    fun updatePlace(json: JsonMap, index: Int) {
        while(index >= place.size) place.add(PlaceModel())
        withPlace(index) { place ->
            place.parse(json)
            placeObservers.forEach { it(place, index) }
        }
    }

    fun updateDays(json: JsonMap, index: Int) {
        while(index >= days.size) days.add(DaysModel())
        withDays(index) { days ->
            days.parse(json)
            dayObservers.forEach { it(days, index) }
        }
    }

    fun updateSearchResult(json: JsonMap) {
        this.search.parse(json)
        searchObservers.forEach { it(this.search) }
    }

    fun notify(index: Int) {
        withDays(index) { days ->
            dayObservers.forEach { it(days, index) }
        }
        withPlace(index) { place ->
            placeObservers.forEach { it(place, index) }
        }
    }

    fun find(pos: LatLong): Int {
        this.days.forEachIndexed { index, days ->
            if (days.getLatLong().sphericalDistance(pos) < SAME_PLACE) {
                return index
            }
        }
        return -1
    }
}
