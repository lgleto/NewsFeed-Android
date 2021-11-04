package ipca.example.newsfeed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

object Repository {

    suspend fun fetchPostsOnDB(context: Context) : List<Post> {
        val pts = AppDatabase.getDatabase(context)?.postDao()?.getAllPost()
        return pts?: arrayListOf()
    }

    fun fetchPost(context: Context) : LiveData<List<Post>> = liveData(Dispatchers.IO) {
        emit(fetchPostsOnDB(context))
        val jsonObject =
            Backend
                .getAllPost("top-headlines?country=pt&category=business&apiKey=1765f87e4ebc40229e80fd0f75b6416c")

        val posts = arrayListOf<Post>()
        if (jsonObject.get("status").equals("ok")) {
            val jsonArrayArticles = jsonObject.getJSONArray("articles")
            for (index in 0 until jsonArrayArticles.length()) {
                val jsonArticle: JSONObject = jsonArrayArticles.get(index) as JSONObject
                val post = Post.fromJson(jsonArticle)
                posts.add(post)
            }
        }

        posts.forEach{ p->
            AppDatabase.getDatabase(context)
                ?.postDao()
                ?.insert(p)
        }

        emit(fetchPostsOnDB(context))
    }

}