package view

import Config
import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.ApplicationWindow
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import controller.Controller
import kotlin.system.exitProcess

class Window(app: Application) {
    private val window = ApplicationWindow(app)

    init {
        val box = Box(Orientation.VERTICAL, 0)
        val days = Days()
        val place = Place()
        val header = Header(window)
        val search = Search()
        val map = Map()

        Controller.map = map.mapView

        window.title = Config.appTitle
        window.titlebar = header.headerBar

        box.append(place.box)

        box.append(days.icons)
        box.append(search.box)
        box.append(map.mapView.drawingArea)
        map.mapView.drawingArea.vexpand = GTK.TRUE
        map.mapView.drawingArea.hexpand = GTK.TRUE

        window.setDefaultSize(Config.window_width, Config.window_height)

        window.child =box
        window.onShow {
            map.initModel()
            Controller.loadModelFromFile()
        }

        window.onDestroy {
            exitProcess(0)
        }

        window.show()
    }
}
