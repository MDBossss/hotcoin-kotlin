package android.tvz.hr.hotcoin.ui.news

import android.tvz.hr.hotcoin.api.NewsService
import android.tvz.hr.hotcoin.model.NewsResponse
import android.tvz.hr.hotcoin.util.Constants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val newsService: NewsService) : ViewModel() {

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse> = _newsResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getNews() {
        newsService.getNews(Constants.NEWS_QUERY, Constants.NEWS_API_KEY).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    if (newsResponse != null) {
                        // Handle successful response with news data
                        _newsResponse.value = newsResponse!!
                    } else {
                        // Handle empty response (potentially an error from the server)
                        _error.value = "Empty response from server"
                    }
                } else {
                    // Handle HTTP error (e.g., 404 Not Found, 500 Internal Server Error)
                    _error.value = "Network error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle other errors during network request
                _error.value = "Error fetching news: ${t.message}"
            }
        })
    }

}