package android.tvz.hr.hotcoin.ui.charts

import android.tvz.hr.hotcoin.api.CoinService
import android.tvz.hr.hotcoin.model.Coin
import android.tvz.hr.hotcoin.model.NewsResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChartsViewModel(private val coinService: CoinService) : ViewModel() {

    private val _coinsResponse = MutableLiveData<List<Coin>>()
    val coinsResponse: LiveData<List<Coin>> = _coinsResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun getCoins(){
        coinService.getCoins().enqueue(object: Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                if(response.isSuccessful){
                    val coinsResponse = response.body()
                    if(coinsResponse != null){
                        _coinsResponse.value = coinsResponse!!
                    }
                    else{
                        _error.value = "Failed to get coins"
                    }
                }else{
                    _error.value = "Network error: ${response.code()}"
                }
            }


            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                _error.value = "Error fetching coins: ${t.message}"
            }
        })
    }
}