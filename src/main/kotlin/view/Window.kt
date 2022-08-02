package view

import ch.bailu.gtk.gtk.*
import config.CSS
import config.Layout
import config.Strings
import controller.Controller
import kotlin.system.exitProcess

class Window(app: Application) {
    private val window = ApplicationWindow(app)

    init {
        val box = Box(Orientation.VERTICAL, 0)
        val place = Place()
        val hours = Hours()
        val days = Days(hours)
        val header = Header(window)
        val map = Map()
        val overlay = Overlay()

        Controller.map = map.mapView
        overlay.child = map.mapView.drawingArea

        window.title = Strings.appTitle
        window.titlebar = header.headerBar

        CSS.addStyleProvider(window)
        box.append(place.box)
        box.append(days.icons)
        box.append(hours.scroller)

        overlay.addOverlay(Search(app).box)
        overlay.addOverlay(Navigation().box)
        overlay.addOverlay(Select().box)
        overlay.addOverlay(Spinner().box)
        box.append(overlay)

        window.setDefaultSize(Layout.window_width, Layout.window_height)

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
