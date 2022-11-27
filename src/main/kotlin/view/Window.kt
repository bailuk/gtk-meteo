package view

import ch.bailu.gtk.glib.Glib
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.lib.bridge.CSS
import ch.bailu.gtk.lib.handler.action.ActionHandler
import ch.bailu.gtk.type.Str
import config.Files
import config.Layout
import config.Strings
import controller.Controller
import kotlin.system.exitProcess

class Window(app: Application) {
    companion object {
        const val MESSAGE_TIMEOUT = 8
    }
    private val window = ApplicationWindow(app)

    init {
        val box = Box(Orientation.VERTICAL, 0)
        val place = Place()
        val hours = Hours()
        val days = Days(hours)
        val header = Header(app)
        val map = Map()
        val overlay = Overlay()

        Controller.withMap = { cb -> cb(map.mapView) }
        overlay.child = map.mapView.drawingArea

        window.title = Strings.appTitle
        window.titlebar = header.headerBar

        CSS.addProviderForDisplay(window.display, Files.appCss)
        box.append(place.box)
        box.append(days.icons)
        box.append(hours.revealer)

        box.append(Revealer().apply {
            val label = Label(Str.NULL).apply {
                wrapMode = WrapMode.CHAR
                wrap = true
                addCssClass("error-message")
            }

            child = label
            var timers = 0

            Controller.showError = { message ->
                revealChild = if (message.isEmpty()) {
                    false
                } else {
                    label.setText(message)
                    timers ++ // Ignore previous timeouts
                    Glib.timeoutAddSeconds(MESSAGE_TIMEOUT, { _self, _ ->
                        timers--
                        if (timers == 0) { // Ignore if there is another timer active
                            Controller.showError("")
                        }
                        _self.unregister() // Remove reference to callback (for garbage collection)
                        false // Do not call again
                    }, null)
                    true
                }
            }
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

        ActionHandler.get(app, "about").onActivate { ->
            About.show(window)
        }
        ActionHandler.get(app, "auto-center", false).onToggle {
            print("Auto center: $it")
        }
        window.show()
    }
}
