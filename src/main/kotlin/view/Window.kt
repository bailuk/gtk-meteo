package view

import ch.bailu.gtk.glib.Glib
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.ApplicationWindow
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.Overlay
import ch.bailu.gtk.gtk.Revealer
import ch.bailu.gtk.gtk.WrapMode
import ch.bailu.gtk.lib.bridge.CSS
import ch.bailu.gtk.lib.handler.action.ActionHandler
import ch.bailu.gtk.type.Str
import config.Files
import config.Keys
import config.Layout
import config.Strings
import controller.Controller
import controller.Prefs
import kotlin.system.exitProcess

class Window(app: Application) {
    companion object {
        const val MESSAGE_TIMEOUT = 8
    }
    private val window = ApplicationWindow(app)

    init {
        val box = Box(Orientation.VERTICAL, 0)
        val header = Header(app)
        val map = Map()
        val overlay = Overlay()
        val weather = Weather()
        val intro = Intro()

        Controller.withMap = { cb -> cb(map.mapView) }
        overlay.child = map.mapView.drawingArea

        window.title = Strings.appTitle
        window.titlebar = header.headerBar
        window.iconName = Strings.appID

        CSS.addProviderForDisplay(window.display, Files.APP_CSS)
        box.append(intro.label)
        box.append(weather.box)

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

        window.setDefaultSize(Layout.WINDOW_WIDTH, Layout.WINDOW_HEIGHT)

        window.child = box
        window.onShow {
            map.initModel()
            Controller.loadModelFromFile()
        }

        window.onDestroy {
            exitProcess(0)
        }

        ActionHandler.get(app, Keys.ABOUT.name).onActivate { ->
            About.show(window)
        }
        ActionHandler.get(app, Keys.AUTO_CYCLE.name, Prefs.getAutoCycle()).onToggle {
            Prefs.putAutoCycle(it)
        }
        window.present()
    }
}
