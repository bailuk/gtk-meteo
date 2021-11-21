import ch.bailu.gtk.GTK
import ch.bailu.gtk.gio.ApplicationFlags
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import ch.bailu.gtk.type.Strs
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.gtk.graphics.GtkGraphicFactory
import org.mapsforge.map.gtk.util.TileCacheUtil
import org.mapsforge.map.gtk.view.MapView
import org.mapsforge.map.layer.download.TileDownloadLayer
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik
import java.lang.Exception
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    GTK.init()

    val app = Application(Str("ch.bailu.gtk-meteo"), ApplicationFlags.FLAGS_NONE)

    app.onActivate {
        val window = ApplicationWindow(app)
        val mapView = MapView()
        val box = Box(Orientation.VERTICAL, 2)
        val icons = Box(Orientation.HORIZONTAL, 2)

        box.packStart(mapView.drawingArea, GTK.TRUE, GTK.TRUE, 2)
        box.packEnd(icons, GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("clearsky_day.svg"), GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("cloudy.svg"), GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("fog.svg"), GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("lightrain.svg"), GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("heavysnow.svg"), GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("heavysleet.svg"), GTK.FALSE, GTK.TRUE, 2)
        icons.packStart(getIcon("lightsnow.svg"), GTK.FALSE, GTK.TRUE, 2)



        window.add(box)
        window.title = Str("Map")
        window.setSizeRequest(500,500)

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
        }

        window.onDestroy {
            exitProcess(0)
        }

        window.showAll()
    }

    app.run(args.size, Strs(args))
}


fun getIcon(name: String): Image {
    val image = Image()

    try {
        val input = image.javaClass.getResource("/svg/${name}").openStream()
        val pixbuf = ch.bailu.gtk.bridge.Image.load(input, 64, 64)

        image.setFromPixbuf(pixbuf);
    } catch (e: Exception) {
        println(e.message)
    }

    image.setSizeRequest(64,64)
    return image
}