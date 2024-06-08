package android.tvz.hr.hotcoin.ui.bookmarked

import android.content.Context
import android.tvz.hr.hotcoin.api.BookmarksService
import android.tvz.hr.hotcoin.local.UserDao
import android.tvz.hr.hotcoin.local.UserDatabase
import android.tvz.hr.hotcoin.local.UserDatabaseHelper
import android.tvz.hr.hotcoin.model.Article
import android.tvz.hr.hotcoin.model.User
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkedViewModel(private val context: Context, private val bookmarksService: BookmarksService) : ViewModel() {
    private val _bookmarksResponse = MutableLiveData<List<Article>>()
    val bookmarksResponse: LiveData<List<Article>> = _bookmarksResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _deleteResponse = MutableLiveData<String>()
    val deleteResponse: LiveData<String> = _error




    fun getBookmarkedArticles(){

        // Initialize the Room database for the user and get user
        val db = UserDatabaseHelper.getInstance(context)
        val user = db.userDao().getUser()

        bookmarksService.getAllBookmarkedArticles(user.id).enqueue(object : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                if(response.isSuccessful){
                    val bookmarksResponse = response.body()
                    if(bookmarksResponse != null){
                        _bookmarksResponse.value = bookmarksResponse!!
                    }else{
                        _error.value = "Failed to fetch bookmarked articles"
                    }
                }else{
                    _error.value = "Network error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                _error.value = "Error fetching news: ${t.message}"
            }
        })
    }

    fun deleteArticle(article: Article){

        // Initialize the Room database for the user and get user
        val db = UserDatabaseHelper.getInstance(context)
        val user = db.userDao().getUser()

        bookmarksService.deleteArticle(article.title,user.id).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    _deleteResponse.value = "Article deleted successfully"
                }
                else{
                    _deleteResponse.value = "Failed to delete article"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _deleteResponse.value = "Error deleting article: ${t.message}"
            }
        })
    }

}