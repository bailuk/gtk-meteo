package controller

import ch.bailu.gtk.gtk.Spinner
import ch.bailu.gtk.type.Str
import model.Model
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.gtk.view.MapView

object Controller {
    private val rest = Rest()
    private val slot = Slot()

    var showError : (message: String) -> Unit = { message -> if(message.isNotEmpty()) println(message) }
    var withMap : (cb: (mapView: MapView) -> Unit) -> Unit = {}
    var withSpinner: (cb: (spinner: Spinner) -> Unit) -> Unit = {}

    fun addMapCenterToModelAndLoad() {
        withMap {
            val latLong = it.model.mapViewPosition.center

            showError("")
            try {
                selectSlotFor(latLong)

                rest.saveDays(slot.selected, latLong) { restClient ->
                    if (restClient.ok) Model.updateDays(restClient.json, slot.selected)
                    updateSpinner()
                }

                rest.savePlace(slot.selected, latLong) { restClient ->
                    if (restClient.ok) Model.updatePlace(restClient.json, slot.selected)
                    updateSpinner()
                }
                updateSpinner()
            } catch (e: IndexOutOfBoundsException) {
                val message = e.message
                if (message is String) {
                    showError(message)
                }
            }
        }
    }

    private fun selectSlotFor(latLong: LatLong) {
        val found = Model.find(latLong)
        if (found > -1) {
            slot.select(found)
        } else {
            slot.selectNextFreeOrOldestSlot()
        }
        rest.setSlots(slot)
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

    fun loadModelFromFile() {
        rest.setSlots(slot)
        rest.forEachDay { index, restClient ->
            Model.updateDays(restClient.json, index)
        }
        rest.forEachPlace { index, restClient ->
            Model.updatePlace(restClient.json, index)
        }
        Model.updateSearchResult(rest.search.json)
        showPlace()
    }

    fun showPlace() {
        Model.withDays(slot.selected) {
            centerMap(it.getLatLong())
        }
    }

    fun search(search: String) {
        if (search.isNotEmpty()) {
            rest.search(search) { it ->
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
        withSpinner { spinner->
            if (RestClient.downloads > 0) {
                spinner.start()
            } else {
                spinner.stop()
            }
        }
    }

    fun selectNextSlot() {
        slot.selectNext()
        Model.notify(slot.selected)
    }

    fun notifySelectedSlot() {
        Model.notify(slot.selected)
    }

    fun updateSelectedSlot() {
        Model.withDays(slot.selected) { days->
            rest.saveDays(slot.selected, days.getLatLong()) { rest ->
                if (rest.ok) Model.updateDays(rest.json, slot.selected)
                updateSpinner()
            }
        }
        updateSpinner()
    }

    fun isSelectedSlot(index: Int): Boolean {
        return slot.selected == index
    }

    fun selectSlot(index: Int) {
        slot.select(index)
        Model.notify(slot.selected)
    }

    fun isSelectedSlotLocked(): Boolean {
        return LockFiles.isLocked(slot.selected)
    }

    fun toggleLockSelectedSlot() {
        LockFiles.toggleLock(slot.selected)
    }
}
