package config

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle

object I18n {
    private val locale = Locale.getDefault()
    private val res = ResourceBundle.getBundle("i18n.strings", locale)


    fun getString(key: Keys): String {
        return getString(key.name.lowercase())
    }

    fun getString(name: String): String {
        return try {
            res.getString(name)
        } catch (e: MissingResourceException) {
            "-"
        }
    }

    fun getDayOfWeek(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
    }
}
