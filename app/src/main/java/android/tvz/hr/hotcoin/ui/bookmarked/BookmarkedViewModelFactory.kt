package android.tvz.hr.hotcoin.ui.bookmarked

import android.tvz.hr.hotcoin.api.BookmarksService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookmarkedViewModelFactory(private val bookmarksService: BookmarksService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(BookmarkedViewModel::class.java)){
            BookmarkedViewModel(bookmarksService) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}