import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.type.Strs
import config.Strings.appID
import view.Window

fun main(args: Array<String>) {
    Application(appID, 0).apply {
        onActivate {
            Window(this)
        }
        run(args.size, Strs(args))
    }
}
