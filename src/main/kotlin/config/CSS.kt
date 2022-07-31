package config

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Bytes
import ch.bailu.gtk.type.Str
import java.io.InputStream

object CSS {
    private val styleProvider = StyleProvider(CssProvider().apply {
        val css = resourceToStr("app.css")
        loadFromData(css, -1)
        css.destroy()
    }.cast())

    fun addStyleProvider(window: ApplicationWindow) {
        StyleContext.addProviderForDisplay(window.display, styleProvider, GtkConstants.STYLE_PROVIDER_PRIORITY_USER)
    }

    private fun resourceToStr(name: String): Str {
        val css = resourceToBytes(name)
        return Str(Bytes(css).cast())
    }

    private fun resourceToBytes(name: String): ByteArray {
        try {
            javaClass.classLoader.getResourceAsStream(name).use {
                if (it is InputStream) {
                    return it.readAllBytes()
                } else {
                    return byteArrayOf()
                }
            }
        } catch (e: Exception) {
            return byteArrayOf()
        }
    }
}
