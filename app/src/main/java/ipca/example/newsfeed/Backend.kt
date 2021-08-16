package ipca.example.newsfeed

import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object Backend {

    val BASE_AIP = "https://newsapi.org/v2/"

    fun getAllPost(endpoint : String, callback: ((JSONObject)->Unit)?){
        GlobalScope.launch(Dispatchers.IO) {
            val urlc : HttpURLConnection =
                URL(BASE_AIP + endpoint)
                    .openConnection() as HttpURLConnection
            urlc.setRequestProperty("User-Agent","Test")
            urlc.setRequestProperty("Connection","close")
            urlc.connectTimeout = 1500
            urlc.connect()
            val stream = urlc.inputStream
            val isReader = InputStreamReader(stream)
            val brin  = BufferedReader(isReader)
            var str : String = ""

            var keepReading = true
            while(keepReading) {
                val line = brin.readLine()
                if (line == null){
                    keepReading = false
                }else {
                    str += line
                }
            }

            val jsonObject = JSONObject(str)
            GlobalScope.launch (Dispatchers.Main){
                callback?.let {
                    callback.invoke(jsonObject)
                }
            }
        }
    }

    fun getImageFromUrl( url:String , imageView: ImageView) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val input = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(input)
                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }
            }catch (e: Exception) {
                println(e.localizedMessage)
                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageResource(R.mipmap.icon_article)
                }
            }

        }
    }
}