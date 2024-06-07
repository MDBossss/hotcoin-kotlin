package android.tvz.hr.hotcoin.ui.charts

import android.os.Bundle
import android.tvz.hr.hotcoin.api.CoinService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.tvz.hr.hotcoin.databinding.FragmentChartsBinding
import android.tvz.hr.hotcoin.util.Constants
import android.tvz.hr.hotcoin.util.RetrofitHelper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null
    private lateinit var coinService: CoinService
    private lateinit var chartsAdapter: ChartsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize the service it pass to the factory to pass to the viewmodel
        coinService = RetrofitHelper().createService(CoinService::class.java,Constants.COINGECKO_API_URL)
        val chartsViewModel =
            ViewModelProvider(this, ChartsViewModelFactory(coinService)).get(ChartsViewModel::class.java)

        //Binding
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.chartsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with empty list before calling the api

        chartsAdapter = ChartsAdapter(emptyList())
        binding.chartsRecyclerView.adapter = chartsAdapter

        // Fetching and observing the coins from the viewmodel
        chartsViewModel.getCoins()

        chartsViewModel.coinsResponse.observe(viewLifecycleOwner){ coinsResponse ->
            if(coinsResponse != null){
                chartsAdapter = ChartsAdapter(coinsResponse)
                binding.chartsRecyclerView.adapter = chartsAdapter
            }
        }

        chartsViewModel.error.observe(viewLifecycleOwner){ error ->
            if(error != null){
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}