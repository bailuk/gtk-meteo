package controller

import ch.bailu.gtk.glib.Glib
import config.Strings
import okhttp3.*
import lib.json.Json
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

                Controller.showError("Failed to download from '$url'")
                callBack(observer)
            }

            override fun onResponse(call: Call, response: Response) {
                downloads--
                ok = true

                val jsonText = start + response.body?.string() + end
                file.writeText(jsonText)
                json = Json.parse(jsonText)

                callBack(observer)
            }
        })
    }

    private fun callBack(observer: (RestClient)->Unit) {
        Glib.idleAdd({ self, _ ->
            observer(this@RestClient)
            self.unregister()
            false
        }, null)

    }

    private fun getCall(url: String) : Call {
        return client.newCall(getRequest(url))
    }

    private fun getRequest(url: String) : Request {
        return Request.Builder()
                .url(url).header("User-Agent", Strings.userAgent)
                .build()
    }
}
