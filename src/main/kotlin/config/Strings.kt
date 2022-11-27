package config

import ch.bailu.gtk.type.Str

object Strings {
    const val userAgent = "gtk-meteo"

    val appID = Str("ch.bailu.gtk-meteo")
    val appTitle = Str("GTK Meteo")
    val version = Str("v0.1.0")

    val copyright = Str(
        """
            © 2021, 2022 Lukas Bai, MIT License,
            © Map and data OSM contributors ODbL 1.0,
            © Icons and weather data MET Norway
        """.trimIndent()
    )

    val website = Str("https://github.com/bailuk/gtk-meteo")

    // UX
    const val close = "Close"
    const val info = "Info…"
    const val autoCenter = "Center map when changing place"

    // CSS
    val linked = Str("linked")
    val mapCenter = Str("map-center")

    // Defaults
    val empty = Str("")
}
