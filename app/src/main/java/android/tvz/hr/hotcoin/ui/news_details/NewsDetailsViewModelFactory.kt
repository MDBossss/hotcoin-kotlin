package android.tvz.hr.hotcoin.ui.news_details

import android.content.Context
import android.tvz.hr.hotcoin.api.BookmarksService
import android.tvz.hr.hotcoin.api.NewsService
import android.tvz.hr.hotcoin.ui.news_details.NewsDetailsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDetailsViewModelFactory(private val context: Context, private val bookmarksService: BookmarksService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(NewsDetailsViewModel::class.java)){
            NewsDetailsViewModel(context,bookmarksService) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}