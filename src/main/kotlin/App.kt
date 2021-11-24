import ch.bailu.gtk.GTK
import ch.bailu.gtk.gio.ApplicationFlags
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import ch.bailu.gtk.type.Strs
import org.mapsforge.core.graphics.Canvas
import org.mapsforge.core.graphics.Color
import org.mapsforge.core.graphics.Paint
import org.mapsforge.core.model.BoundingBox
import org.mapsforge.core.model.LatLong
import org.mapsforge.core.model.Point
import org.mapsforge.map.gtk.graphics.GtkGraphicFactory
import org.mapsforge.map.gtk.util.TileCacheUtil
import org.mapsforge.map.gtk.view.MapView
import org.mapsforge.map.layer.Layer
import org.mapsforge.map.layer.download.TileDownloadLayer
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik
import org.mapsforge.map.model.DisplayModel
import view.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    GTK.init()

    val app = Application(Str("ch.bailu.gtk-meteo"), 0)

    app.onActivate {
        val window = ApplicationWindow(app)
        val mapView = MapView()
        val box = Box(Orientation.VERTICAL, 0)
        val days = Days()
        val place = Place()
        val header = Header("GTK Meteo")
        val search = Search()
        val status = Status()

        window.titlebar = header.headerBar
        window.icon = IconMap.getPixbuf("clearsky_day", 128)

        box.packStart(place.box, GTK.FALSE, GTK.TRUE, 2)

        box.packStart(days.icons, GTK.FALSE, GTK.TRUE, 2)
        box.packStart(search.searchBar, GTK.FALSE, GTK.TRUE, 0)
        box.packStart(mapView.drawingArea, GTK.TRUE, GTK.TRUE, 0)
        box.packEnd(status.box, GTK.FALSE, GTK.TRUE, 0)

        box.showAll()

        window.add(box)
        window.setSizeRequest(720/2,1440/2)

        window.onShow {
            OpenStreetMapMapnik.INSTANCE.userAgent = "mapsforge-samples-gtk"

            val tileCache = TileCacheUtil.createTileCache(mapView.model)
            val tileDownloadLayer = TileDownloadLayer(tileCache, mapView.model.mapViewPosition, OpenStreetMapMapnik.INSTANCE, GtkGraphicFactory.INSTANCE)

            mapView.layerManager.layers.add(tileDownloadLayer)
            tileDownloadLayer.start()

            mapView.setZoomLevelMin(OpenStreetMapMapnik.INSTANCE.zoomLevelMin)
            mapView.setZoomLevelMax(OpenStreetMapMapnik.INSTANCE.zoomLevelMax)

            mapView.setZoomLevel(14)
            mapView.model.mapViewPosition.center = LatLong(47.35,7.9)

            mapView.model.displayModel.setFixedTileSize(256)
        }

        window.onDestroy {
            exitProcess(0)
        }

        window.showAll()
    }

    app.run(args.size, Strs(args))
}
