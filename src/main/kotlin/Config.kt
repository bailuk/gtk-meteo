import ch.bailu.gtk.type.Str
import java.io.File

object Config {
    val window_width = 720/2
    val window_height = 1440/2
    val userAgent = "gtk-meteo"
    val appID = Str("ch.bailu.gtk-meteo")
    val appTitle = Str("GTK Meteo")

    val version = Str("v0.1.0")

    val copyright = Str(
        """
            © 2021, 2022 Lukas Bai, MIT License,
            © Map and data OSM contributors ODbL 1.0,
            © Icons and weather data MET Norway
        """.trimIndent())

    val website = Str("https://github.com/bailuk/gtk-meteo")

    val configDirectory = File(System.getProperty("user.home") + "/.config/$userAgent/")

    init {
        configDirectory.mkdirs()
    }

    fun getJsonFile(name : String) : File {
        val jsonName = if (name.endsWith(".json")) {
            name
        } else {
            "$name.json"
        }

        return File(configDirectory, jsonName)
    }
}
