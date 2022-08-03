package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
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
        val header = Header(window, app)
        val map = Map()
        val overlay = Overlay()


        Controller.withMap = { cb -> cb(map.mapView) }
        overlay.child = map.mapView.drawingArea

        window.title = Strings.appTitle
        window.titlebar = header.headerBar

        CSS.addStyleProvider(window)
        box.append(place.box)
        box.append(days.icons)
        box.append(hours.scroller)

        box.append(InfoBar().apply {
            showCloseButton = GTK.TRUE
            val label = Label(Str.NULL)
            addChild(label)
            messageType = MessageType.ERROR
            onResponse { hide() }

            Controller.showError = { message ->
                if (message.isEmpty()) {
                    hide()
                } else {
                    label.text = Str(message)
                    show()
                }
            }
            visible = GTK.FALSE
        })

        overlay.addOverlay(Search(app).box)
        overlay.addOverlay(Navigation().box)
        overlay.addOverlay(Select().box)
        overlay.addOverlay(Spinner().box)
        box.append(overlay)

        window.setDefaultSize(Layout.windowWidth, Layout.windowHeight)

        window.child = box
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
