package controller

import config.Keys
import java.util.prefs.Preferences

object Prefs {
    private val prefs = Preferences.userRoot().node(this.javaClass.name)

    fun putAutoCycle(value: Boolean) {
        prefs.putBoolean(Keys.AUTO_CYCLE, value)
    }

    fun getAutoCycle(): Boolean {
        return prefs.getBoolean(Keys.AUTO_CYCLE, false)
    }
}
