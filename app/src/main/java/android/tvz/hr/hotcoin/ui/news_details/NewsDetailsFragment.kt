package android.tvz.hr.hotcoin.ui.news_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.tvz.hr.hotcoin.api.BookmarksService
import android.tvz.hr.hotcoin.databinding.FragmentNewsDetailsBinding
import android.tvz.hr.hotcoin.model.Article
import android.tvz.hr.hotcoin.util.Constants
import android.tvz.hr.hotcoin.util.RetrofitHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()
    private lateinit var bookmarksService: BookmarksService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize the service to pass to the factory to pass to the viewmodel
        bookmarksService = RetrofitHelper().createService(BookmarksService::class.java, Constants.DATABASE_URL)
        val bookmarksViewModel = ViewModelProvider(this, NewsDetailsViewModelFactory(bookmarksService)).get(NewsDetailsViewModel::class.java)

        binding = FragmentNewsDetailsBinding.inflate(inflater,container,false)

        var article: Article =  arguments?.getParcelable("article") ?: args.article ?: throw IllegalArgumentException("Article not found")

        val formattedDate = Instant
            .parse(article.publishedAt)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a"))

        binding.newsSourceTextView.text = article.source.name
        binding.newsTitleTextView.text = article.title
        binding.newsAuthorPublishedAt.text = "Posted by ${article.author}, ${formattedDate}"
        Picasso.get().load(article.urlToImage).into(binding.newsImageView)
        binding.newsContentTextView.text = article.content


        // Set button onclick listeners
        binding.bookmarkButton.setOnClickListener{
            // Call the viewmodel to create the article bookmark
            bookmarksViewModel.createBookmark(article)

        }

        binding.openSourceButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }


        // Observe the create article and wait for response
        bookmarksViewModel.responseMessage.observe(viewLifecycleOwner){responseMessage ->
            if(responseMessage != null){
                Toast.makeText(context,responseMessage,Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }
}