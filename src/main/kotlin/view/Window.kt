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

        window.titlebar = header.headerBar
        window.icon = IconMap.getPixbuf(Config.appIcon, 128)

        box.packStart(place.box, GTK.FALSE, GTK.TRUE, 2)

        box.packStart(days.icons, GTK.FALSE, GTK.TRUE, 2)
        box.packStart(search.box, GTK.FALSE, GTK.TRUE, 0)
        box.packStart(map.mapView.drawingArea, GTK.TRUE, GTK.TRUE, 0)
        box.packEnd(status.box, GTK.FALSE, GTK.TRUE, 0)

        window.setDefaultSize(720/2,1440/2)

        window.add(box)
        window.onShow {
            map.initModel()
            Controller.loadModelFromFile()
        }

        window.onDestroy {
            exitProcess(0)
        }

        window.showAll()
    }
}