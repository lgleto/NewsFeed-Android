package ipca.example.newsfeed

import androidx.room.*
import org.json.JSONObject

@Entity
data class Post ( var title         : String?,
                  var description   : String?,
                  var urlToImage    : String?,
                  @PrimaryKey
                  var url           : String) {

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("title", title)
        jsonObject.put("description", description)
        jsonObject.put("urlToImage", urlToImage)
        jsonObject.put("url", url)

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : Post {
            val post = Post(
                jsonObject.getString("title"),
                jsonObject.getString("description"),
                jsonObject.getString("urlToImage"),
                jsonObject.getString("url")
            )
            return post
        }
    }
}

@Dao
interface PostDao {

    @Query( "SELECT * FROM Post")
    fun getAllPost() : List<Post>

    @Query("SELECT * FROM Post WHERE url=:urlStr")
    fun getPostByUrl(urlStr : String) : Post

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post:Post)

}
