package android.tvz.hr.hotcoin.ui.bookmarked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookmarkedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bookmarked Fragment"
    }
    val text: LiveData<String> = _text
}