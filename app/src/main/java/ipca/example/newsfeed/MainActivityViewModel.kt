package ipca.example.newsfeed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    fun getPosts(context: Context): LiveData<List<Post>> {
        return Repository.fetchPost(context)
    }

}