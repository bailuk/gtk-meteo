package controler

import ch.bailu.gtk.gtk.Spinner
import model.Model
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.gtk.view.MapView

object Controler {
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

    fun withMap(call : (MapView)->Unit) {
        var map = map
        if (map is MapView) {
            call(map)
        }
    }

    private fun updateSpinner() {
        var spinner = spinner

        if (spinner is Spinner) {
            if (RestClient.downloads > 0) {
                spinner.start()
            } else {
                spinner.stop()
            }
        }
    }

    fun loadModelFromFile() {
        Model.updateDays(Rest.days.json)
        Model.updatePlace(Rest.place.json)
        showPlace()
    }

    fun showPlace() {
        withMap { it.model.mapViewPosition.center = LatLong(Model.days.getLatitude(), Model.days.getLongitude()) }
    }

    fun search(search: String) {
        val result = ArrayList<LatLong>()
        Rest.search(search) {
            if (it.ok) {
                it.json.map("result") {
                    var lat = 0.0
                    var lon = 0.0
                    it.string("lat") {
                        lat = it.toDouble()
                    }
                    it.string("lon") {
                        lon = it.toDouble()
                    }
                    result.add(LatLong(lat, lon))
                }

                System.out.println(result.size)

                if (result.size > 0) {
                    withMap { it.model.mapViewPosition.center = result.first() }
                }
            }
            updateSpinner()
        }
        updateSpinner()
    }
}