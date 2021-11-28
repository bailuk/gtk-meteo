package controler

import ch.bailu.gtk.GTK
import ch.bailu.gtk.glib.Glib
import ch.bailu.gtk.type.Pointer
import com.google.gson.Gson
import model.Config
import okhttp3.*
import parser.Json
import parser.JsonMap
import java.io.File
import java.io.IOException

class RestClient(val file: File, private val start: String = "", private val end : String = "") {
    private val client = OkHttpClient()
    private var call = getCall("http://localhost")

    var json = Json.parse(file)
        private set

    var ok = true
        private set

    companion object {
        var downloads = 0
            private set
    }

    @Throws(IOException::class)
    fun download(url: String, observer: (RestClient)->Unit) {
        call.cancel()

        downloads ++

        call = getCall(url)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                downloads--
                ok = false

                Glib.idleAdd({ l: Pointer? ->
                    observer(this@RestClient)
                    GTK.FALSE
                 }, ch.bailu.gtk.Callback.EmitterID())
            }


            override fun onResponse(call: Call, response: Response) {
                downloads--
                ok = true

                val jsonText = start + response.body?.string() + end
                file.writeText(jsonText)
                val jsonParsed = Gson().fromJson(jsonText, Map::class.java)

                json = JsonMap(jsonParsed)

                Glib.idleAdd({ l: Pointer? ->
                    observer(this@RestClient)
                    GTK.FALSE
                }, ch.bailu.gtk.Callback.EmitterID())
            }
        })
    }

    private fun getCall(url: String) : Call {
        return client.newCall(getRequest(url))
    }

    private fun getRequest(url: String) : Request {
        return Request.Builder()
                .url(url).header("User-Agent", Config.userAgent)
                .build()
    }
}