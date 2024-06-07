package android.tvz.hr.hotcoin.ui.news

import android.os.Bundle
import android.tvz.hr.hotcoin.api.NewsService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.tvz.hr.hotcoin.databinding.FragmentNewsBinding
import android.tvz.hr.hotcoin.model.NewsResponse
import android.tvz.hr.hotcoin.util.Constants
import android.tvz.hr.hotcoin.util.RetrofitHelper
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private lateinit var newsService: NewsService
    private lateinit var newsAdapter: NewsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Initialize the service to pass to the factory to pass to the viewmodel
        newsService = RetrofitHelper().createService(NewsService::class.java,Constants.NEWS_API_URL)
        val newsViewModel =
            ViewModelProvider(this,NewsViewModelFactory(newsService)).get(NewsViewModel::class.java)

        // Binding
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with empty list before calling the api
        newsAdapter = NewsAdapter(requireContext(),emptyList())
        binding.newsRecyclerView.adapter = newsAdapter


        // Fetching and observing the news from the viewmodel
        newsViewModel.getNews()

        newsViewModel.newsResponse.observe(viewLifecycleOwner) { newsResponse ->
            if (newsResponse != null) {
                val articles = newsResponse.articles
                newsAdapter = NewsAdapter(requireContext(),articles)
                binding.newsRecyclerView.adapter = newsAdapter
            }
        }

        newsViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
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