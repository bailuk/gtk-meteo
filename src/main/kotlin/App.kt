import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.type.Strs
import view.Window

fun main(args: Array<String>) {
    val app = Application(Config.appID, 0)

    app.onActivate {
        Window(app)
    }

    app.run(args.size, Strs(args))
}
