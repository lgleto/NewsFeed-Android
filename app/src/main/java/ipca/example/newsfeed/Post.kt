package ipca.example.newsfeed

import org.json.JSONObject

class Post {

    var title : String? = null
    var description : String? = null
    var urlToImage : String? = null
    var url : String? = null

    constructor(title: String?, description: String?, urlToImage: String?, url: String?) {
        this.title = title
        this.description = description
        this.urlToImage = urlToImage
        this.url = url
    }

    constructor() {

    }

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
            val post = Post()
            post.title = jsonObject.getString("title")
            post.description = jsonObject.getString("description")
            post.urlToImage = jsonObject.getString("urlToImage")
            post.url = jsonObject.getString("url")

            return post
        }


    }

}