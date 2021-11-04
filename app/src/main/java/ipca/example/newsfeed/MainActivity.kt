package ipca.example.newsfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ipca.example.newsfeed.R.id.textViewTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    // Model
    var posts : List<Post> = arrayListOf()
    val postsAdapter = PostsAdapter()

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewPosts = findViewById<ListView>(R.id.listViewPosts)
        listViewPosts.adapter = postsAdapter

        mainActivityViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainActivityViewModel::class.java)
        mainActivityViewModel.getPosts(this).observe(this, Observer{
            // update UI
            posts  = it
            postsAdapter.notifyDataSetChanged()
        })
    }



    inner class PostsAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return posts.size
        }

        override fun getItem(position: Int): Any {
            return posts[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_post, parent, false)

            val textViewTitle       = rowView.findViewById<TextView >(textViewTitle)
            val textViewDescription = rowView.findViewById<TextView >(R.id.textViewDescription)
            val imageViewPost       = rowView.findViewById<ImageView>(R.id.imageViewPost)

            textViewTitle.text = posts[position].title
            textViewDescription.text = posts[position].description

            posts[position].urlToImage?.let {
                Backend.getImageFromUrl(it, imageViewPost)
            }


            rowView.setOnClickListener {
                Toast.makeText(this@MainActivity, posts[position].title, Toast.LENGTH_LONG).show()

                /*
                val intent = Intent(this@MainActivity, PostDetailActivity::class.java)
                intent.putExtra( PostDetailActivity.EXTRA_ARTICLE , posts[position].toJson().toString() )
                startActivity(intent)
                 */

                val intent = Intent(this@MainActivity, WebViewArticle::class.java)
                intent.putExtra( WebViewArticle.EXTRA_ARTICLE , posts[position].toJson().toString() )
                startActivity(intent)
            }


            return rowView
        }

    }


}