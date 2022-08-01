package model

import parser.JsonMap

object Model {
    private val dayObservers = ArrayList<(Model)->Unit>()
    private val placeObservers = ArrayList<(Model)->Unit>()
    private val searchObservers = ArrayList<(Model)->Unit>()


    var days = DaysModel()
        private set

    var place = PlaceModel()
        private set

    val search = SearchModel()

    fun observePlace(observer : (Model) -> Unit) {
        placeObservers.add(observer)
    }

    fun observeDays(observer : (Model) -> Unit) {
        dayObservers.add(observer)
    }

    fun observeSearch(observer: (Model) -> Unit) {
        searchObservers.add(observer)
    }

    fun updatePlace(place: JsonMap) {
        this.place.parse(place)
        placeObservers.forEach { it(this) }
    }

    fun updateDays(days: JsonMap) {
        this.days.parse(days)
        dayObservers.forEach { it(this) }
    }

    fun updateSearchResult(result: JsonMap) {
        this.search.parse(result)
        searchObservers.forEach { it(this) }
    }
}
