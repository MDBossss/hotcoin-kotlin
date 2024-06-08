package android.tvz.hr.hotcoin.ui.bookmarked

import android.os.Bundle
import android.tvz.hr.hotcoin.api.BookmarksService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.tvz.hr.hotcoin.databinding.FragmentBookmarkedBinding
import android.tvz.hr.hotcoin.model.Article
import android.tvz.hr.hotcoin.util.Constants
import android.tvz.hr.hotcoin.util.RetrofitHelper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

class BookmarkedFragment : Fragment(), BookmarkedAdapter.OnItemClickListener {

    private var _binding: FragmentBookmarkedBinding? = null
    private lateinit var bookmarkedService: BookmarksService
    private lateinit var bookmarkedAdapter: BookmarkedAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize the service to pass ot hte factory to pass the the viewmodel
        bookmarkedService = RetrofitHelper().createService(BookmarksService::class.java,Constants.DATABASE_URL)
        val bookmarkedViewModel =
            ViewModelProvider(this,BookmarkedViewModelFactory(requireContext(),bookmarkedService)).get(BookmarkedViewModel::class.java)

        // Binding
        _binding = FragmentBookmarkedBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.bookmarkedNewsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with empty list before calling the api
        bookmarkedAdapter = BookmarkedAdapter(this,emptyList())
        binding.bookmarkedNewsRecyclerView.adapter = bookmarkedAdapter


        // Fetching and observing the bookmarked articles from the viewmodel
        bookmarkedViewModel.getBookmarkedArticles()

        bookmarkedViewModel.bookmarksResponse.observe(viewLifecycleOwner){ bookmarksResponse ->
            if(bookmarksResponse != null){
                bookmarkedAdapter = BookmarkedAdapter(this,bookmarksResponse)
                binding.bookmarkedNewsRecyclerView.adapter = bookmarkedAdapter
            }
        }

        bookmarkedViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }



        return root
    }

    override fun onDeleteButtonClick(article: Article) {
        val bookmarkedViewModel =
            ViewModelProvider(this,BookmarkedViewModelFactory(requireContext(),bookmarkedService)).get(BookmarkedViewModel::class.java)

        // Delete the article
        bookmarkedViewModel.deleteArticle(article)

        // Wait for response
        bookmarkedViewModel.deleteResponse.observe(viewLifecycleOwner) { deleteResponse ->
            if (deleteResponse != null) {
                Toast.makeText(context, deleteResponse, Toast.LENGTH_SHORT).show()
            }
        }


        // Fetching and observing the bookmarked articles from the viewmodel
        bookmarkedViewModel.getBookmarkedArticles()

        bookmarkedViewModel.bookmarksResponse.observe(viewLifecycleOwner){ bookmarksResponse ->
            if(bookmarksResponse != null){
                bookmarkedAdapter = BookmarkedAdapter(this, bookmarksResponse)
                binding.bookmarkedNewsRecyclerView.adapter = bookmarkedAdapter
            }
        }

        bookmarkedViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}