package config

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import java.util.*

object CSS {
    private val styleProvider = StyleProvider(CssProvider().apply {
        val str = Str(resourceToString(Files.appCss))
        loadFromData(str, -1)
        str.destroy()
    }.cast())

    fun addStyleProvider(window: ApplicationWindow) {
        StyleContext.addProviderForDisplay(window.display, styleProvider, GtkConstants.STYLE_PROVIDER_PRIORITY_USER)
    }

    private fun resourceToString(name: String): String {
        try {
            javaClass.classLoader.getResourceAsStream(name)?.let { inputStream->
                Scanner(inputStream,"UTF-8")
                    .useDelimiter("\\A").use { scanner->
                        return scanner.next()
                    }
            }
        } catch(e: Exception) {
            println(e.message)
        }
        return ""
    }
}
