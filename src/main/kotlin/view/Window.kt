package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.ApplicationWindow
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import controller.Controller
import model.Config
import kotlin.system.exitProcess

class Window(app: Application) {
    val window = ApplicationWindow(app)

    init {
        val box = Box(Orientation.VERTICAL, 0)
        val days = Days()
        val place = Place()
        val header = Header()
        val search = Search()
        val status = Status()
        val map = Map()

        Controller.map = map.mapView

        window.title = Config.appTitle
        window.titlebar = header.headerBar
        //window.icon = IconMap.getPixbuf(Config.appIcon, 128)

        box.append(place.box)

        box.append(days.icons)
        box.append(search.box)
        box.append(map.mapView.drawingArea)
        map.mapView.drawingArea.vexpand = GTK.TRUE
        map.mapView.drawingArea.hexpand = GTK.TRUE

        box.append(status.box)

        window.setDefaultSize(720/2,1440/2)

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