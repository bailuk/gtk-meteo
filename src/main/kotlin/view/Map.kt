package view

import org.mapsforge.core.model.LatLong
import org.mapsforge.map.gtk.graphics.GtkGraphicFactory
import org.mapsforge.map.gtk.util.TileCacheUtil
import org.mapsforge.map.gtk.view.MapView
import org.mapsforge.map.layer.download.TileDownloadLayer
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik

class Map {
    val mapView = MapView()


    init {
        OpenStreetMapMapnik.INSTANCE.userAgent = "mapsforge-samples-gtk"
    }

    fun initModel() {
        val tileCache = TileCacheUtil.createTileCache(mapView.model)
        val downloadLayer = TileDownloadLayer(tileCache, mapView.model.mapViewPosition, OpenStreetMapMapnik.INSTANCE, GtkGraphicFactory.INSTANCE)

        mapView.layerManager.layers.add(downloadLayer)
        downloadLayer.start()

        mapView.setZoomLevelMin(OpenStreetMapMapnik.INSTANCE.zoomLevelMin)
        mapView.setZoomLevelMax(OpenStreetMapMapnik.INSTANCE.zoomLevelMax)

        mapView.setZoomLevel(14)
        mapView.model.mapViewPosition.center = LatLong(47.35,7.9)
        mapView.model.displayModel.setFixedTileSize(256)
    }

}