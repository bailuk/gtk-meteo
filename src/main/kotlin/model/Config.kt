package model

import ch.bailu.gtk.type.Str
import java.io.File

object Config {
    val userAgent = "gtk-meteo"
    val appIcon = "clearsky_day"
    val appID = Str("ch.bailu.gtk-meteo")
    val appTitle = Str("GTK Meteo")

    val osmLicense = Str("© Map and data <a href = \"https://osm.org/copyright\">OSM contributors, ODbL 1.0</a>")
    val metLicense =  Str("© Icons and data <a href = \"https://api.met.no/doc/License\">MET Norway</a>")
    val license = Str("License MIT, code is on <a href = \"https://github.com/bailuk/gtk-meteo\">GitHub</a>")


    val configDirectory = File(System.getProperty("user.home") + "/.config/${userAgent}/")

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