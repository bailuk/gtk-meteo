package model

import org.mapsforge.core.model.LatLong
import parser.JsonMap

class SearchModel {

    private data class Result(val name: String, val latLong: LatLong)

    private val result = ArrayList<Result>()

    fun parse(json: JsonMap) {
        result.clear()
        json.map("result") { it ->
            var lat = 0.0
            var lon = 0.0
            var name = ""
            it.string("lat") {
                lat = it.toDouble()
            }
            it.string("lon") {
                lon = it.toDouble()
            }

            it.string("display_name") {
                name = it
            }
            result.add(Result(name, LatLong(lat, lon)))
        }
    }

    fun withFirst(cb : (String, LatLong) -> Unit) {
        val first = result.firstOrNull()
        if (first is Result) {
            cb(first.name, first.latLong)
        }
    }

    fun forEach(cb: (String, LatLong) -> Unit) {
        result.forEach {
            cb(it.name, it.latLong)
        }
    }
}
