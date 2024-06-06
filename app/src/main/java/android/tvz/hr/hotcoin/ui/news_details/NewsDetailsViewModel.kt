package android.tvz.hr.hotcoin.ui.news_details

import android.tvz.hr.hotcoin.api.BookmarksService
import android.tvz.hr.hotcoin.model.Article
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsViewModel(private val bookmarksService: BookmarksService) : ViewModel() {
    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage


    fun createBookmark(article: Article){
        bookmarksService.createArticle(article).enqueue(object : Callback<Article>{
            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                if(response.isSuccessful){
                    val bookmarkResponse = response.body()
                    if(bookmarkResponse != null){
                        _responseMessage.value = "Successfully bookmarked article"
                    }
                    else{
                        _responseMessage.value = "Article already bookmarked"
                    }

                }
                else{
                    _responseMessage.value = "Failed to bookmark article"
                }

            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                _responseMessage.value = "Error bookmarking article: ${t.message}"
            }
        })
    }
}