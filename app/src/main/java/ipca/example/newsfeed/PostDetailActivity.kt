package ipca.example.newsfeed

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class PostDetailActivity : AppCompatActivity() {

    // Model
    var post : Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val articleString = intent.getStringExtra(EXTRA_ARTICLE)
        post = Post.fromJson(JSONObject(articleString))

        title = post?.title

        findViewById<TextView>(R.id.textViewPostTitle).text = post?.title
        findViewById<TextView>(R.id.textViewPostDescription).text = post?.description

        val imageViewPost = findViewById<ImageView>(R.id.imageViewPost)

        post?.urlToImage?.let {
            Backend.getImageFromUrl(it, imageViewPost )
        }



    }

    companion object {
        val EXTRA_ARTICLE = "article"
    }
}