package controller

import java.util.prefs.Preferences

object Prefs {

    private const val AUTO_CYCLE = "auto-cycle"

    private val prefs = Preferences.userRoot().node(this.javaClass.name)

    fun putAutoCycle(value: Boolean) {
        prefs.putBoolean(AUTO_CYCLE, value)
    }

    fun getAutoCycle(): Boolean {
        return prefs.getBoolean(AUTO_CYCLE, false)
    }
}
