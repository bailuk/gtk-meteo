package controller

import config.Files
import config.Keys
import java.util.prefs.Preferences

object Prefs {
    private val prefs: Preferences

    init {
        System.setProperty("java.util.prefs.userRoot", Files.configDirectory.path)
        prefs = Preferences.userRoot().node("gtk-meteo")
    }
    fun putAutoCycle(value: Boolean) {
        prefs.putBoolean(Keys.AUTO_CYCLE.name, value)
    }

    fun getAutoCycle(): Boolean {
        return prefs.getBoolean(Keys.AUTO_CYCLE.name, false)
    }
}
