package config

import config.Strings.userAgent
import java.io.File

object Files {

    private val configHome = (System.getenv("XDG_CONFIG_HOME")
        ?: (System.getProperty("user.home") + "/.config")) + "/$userAgent"
    val configDirectory = File(configHome)

    init {
        println("CONFIG_HOME: $configHome")
        configDirectory.mkdirs()
    }

    fun getJsonFile(name: String): File {
        val jsonName = if (name.endsWith(".json")) {
            name
        } else {
            "$name.json"
        }

        return File(configDirectory, jsonName)
    }

    const val appCss = "/app.css"
}
