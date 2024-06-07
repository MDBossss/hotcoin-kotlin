package android.tvz.hr.hotcoin.ui.charts

import android.tvz.hr.hotcoin.api.CoinService
import android.tvz.hr.hotcoin.api.NewsService
import android.tvz.hr.hotcoin.ui.news.NewsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChartsViewModelFactory(private val coinService: CoinService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ChartsViewModel::class.java)){
            ChartsViewModel(coinService) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}