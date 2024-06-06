package android.tvz.hr.hotcoin.ui.news_details

import android.tvz.hr.hotcoin.api.BookmarksService
import android.tvz.hr.hotcoin.api.NewsService
import android.tvz.hr.hotcoin.ui.news_details.NewsDetailsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDetailsViewModelFactory(private val bookmarksService: BookmarksService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(NewsDetailsViewModel::class.java)){
            NewsDetailsViewModel(bookmarksService) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}