import ch.bailu.gtk.gio.ApplicationFlags
import ch.bailu.gtk.adw.Application
import ch.bailu.gtk.type.Strs
import config.Strings.appID
import view.Window

fun main(args: Array<String>) {
    Application(appID, ApplicationFlags.FLAGS_NONE).apply {
        onActivate {
            Window(this)
        }
        run(args.size, Strs(args))
    }
}
