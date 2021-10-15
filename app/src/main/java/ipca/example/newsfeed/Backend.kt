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
    private val client = OkHttpClient()

    fun getAllPost(endpoint : String, callback: ((JSONObject)->Unit)?){
        GlobalScope.launch(Dispatchers.IO) {

            val request = Request.Builder()
                .url(BASE_AIP + endpoint)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful){
                    val jsonObjectError = JSONObject()
                    jsonObjectError.put("status", "error")
                    jsonObjectError.put("message", "Unexpected code $response")
                    callback.invoke(jsonObject)
                }else{
                    val jsonObject = JSONObject(str)
                    GlobalScope.launch (Dispatchers.Main){
                        callback?.let {
                            callback.invoke(jsonObject)
                        }
                    }
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