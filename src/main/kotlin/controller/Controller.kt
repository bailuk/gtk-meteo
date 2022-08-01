package controller

import ch.bailu.gtk.gtk.Spinner
import model.Model
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.gtk.view.MapView

object Controller {
    var map : MapView? = null
    var spinner : Spinner? = null

    fun loadModelFromEndpoint() {
        withMap {
            val pos = it.model.mapViewPosition.center
            Rest.saveDays(pos) {
                if (it.ok) {
                    Model.updateDays(it.json)
                }
                updateSpinner()
            }

            Rest.savePlace(pos) {
                if (it.ok) Model.updatePlace(it.json)
                updateSpinner()
            }
            updateSpinner()
        }
    }

    fun zoomIn() {
        withMap { it.model.mapViewPosition.zoomIn() }
    }

    fun zoomOut() {
        withMap { it.model.mapViewPosition.zoomOut() }
    }

    fun centerMap(latLong: LatLong) {
        withMap { it.model.mapViewPosition.center = latLong }
    }

    private fun withMap(call : (MapView)->Unit) {
        val map = map
        if (map is MapView) {
            call(map)
        }
    }

    fun loadModelFromFile() {
        Model.updateDays(Rest.days.json)
        Model.updatePlace(Rest.place.json)
        Model.updateSearchResult(Rest.search.json)
        showPlace()
    }

    fun showPlace() {
        centerMap(LatLong(Model.days.getLatitude(), Model.days.getLongitude()))
    }

    fun search(search: String) {
        if (search.isNotEmpty()) {
            Rest.search(search) { it ->
                if (it.ok) {
                    Model.updateSearchResult(it.json)
                    Model.search.withFirst { _, latLong ->
                        centerMap(latLong)
                    }
                }
                updateSpinner()
            }
            updateSpinner()
        }
    }


    private fun updateSpinner() {
        val spinner = spinner

        if (spinner is Spinner) {
            if (RestClient.downloads > 0) {
                spinner.start()
            } else {
                spinner.stop()
            }
        }
    }
}
