package config

import config.Strings.userAgent
import java.io.File

object Files {
    val configDirectory =
        File(System.getProperty("user.home") + "/.config/$userAgent/")

    init {
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

    const val appCss = "app.css"
}
