package android.tvz.hr.hotcoin.ui.news_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.tvz.hr.hotcoin.databinding.FragmentNewsDetailsBinding
import android.tvz.hr.hotcoin.model.Article
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            // TODO: ADD BOOKMARK API CALL
        }

        binding.openSourceButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }



        return binding.root
    }
}