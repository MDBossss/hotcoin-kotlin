package android.tvz.hr.hotcoin.ui.news

import android.tvz.hr.hotcoin.api.NewsService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory(private val newsService: NewsService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            NewsViewModel(newsService) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}