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
        val newsViewModel =
            ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with empty list before calling the api
        newsAdapter = NewsAdapter(emptyList())
        binding.newsRecyclerView.adapter = newsAdapter

        newsService = RetrofitHelper().createService(NewsService::class.java,Constants.NEWS_API_URL)

        newsService.getNews(Constants.NEWS_QUERY,Constants.NEWS_API_KEY).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful){
                    val newsResponse = response.body()
                    if(newsResponse != null){
                        val articles = newsResponse.articles
                        newsAdapter = NewsAdapter(articles)
                        binding.newsRecyclerView.adapter = newsAdapter

                    }
                }else{
                    Toast.makeText(context, "Failed to get articles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
            }
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}