package ipca.example.newsfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import org.json.JSONObject

class WebViewArticle : AppCompatActivity() {

    // Model
    var post : Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_article)

        val articleString = intent.getStringExtra(EXTRA_ARTICLE)
        post = Post.fromJson(JSONObject(articleString))
        title = post?.title
        val webView = findViewById<WebView>(R.id.webView)
        post?.url?.let {
            webView.loadUrl(it)
        }
        
    }

    companion object {
        val EXTRA_ARTICLE = "article"
    }
}