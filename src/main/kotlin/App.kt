import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Strs
import model.Config
import view.Window

fun main(args: Array<String>) {
    GTK.init()

    val app = Application(Config.appID, 0)

    app.onActivate {
        Window(app)
    }

    app.run(args.size, Strs(args))
}
