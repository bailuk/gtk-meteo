package lib.icons

import ch.bailu.gtk.gdk.Paintable
import ch.bailu.gtk.gdk.Texture
import ch.bailu.gtk.gdkpixbuf.Pixbuf
import ch.bailu.gtk.gobject.Object
import ch.bailu.gtk.lib.bridge.Image as ImageBridge

object IconMap {
    private data class IconId (val name: String, val size: Int)

    private val pixbufs = HashMap<IconId, Pixbuf>()

    private fun getPixbuf(name: String, size: Int): Pixbuf {
        return try {
            _getPixbuf(name, size)
        } catch (e: Exception) {
            println("Image resource not found: $name")
            _getPixbuf("none", size)
        }
    }

    private fun _getPixbuf(name: String, size: Int): Pixbuf {
        var result = pixbufs[IconId(name, size)]

        return if (result == null) {
            val input = IconMap.javaClass.getResourceAsStream("/svg/${name}.svg")
            result = ImageBridge.load(input, size, size)
            pixbufs[IconId(name, size)] = result
            (result as Object).ref()
            result
        } else {
            result
        }
    }

    fun getPaintable(name: String, size: Int): Paintable {
        val texture = Texture.newForPixbufTexture(getPixbuf(name, size))
        return texture.asPaintable()
    }
}
