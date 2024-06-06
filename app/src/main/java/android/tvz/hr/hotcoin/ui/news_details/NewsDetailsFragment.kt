package android.tvz.hr.hotcoin.ui.news_details

import android.os.Bundle
import android.tvz.hr.hotcoin.databinding.FragmentNewsDetailsBinding
import android.tvz.hr.hotcoin.model.Article
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater,container,false)

        val article: Article = args.article

        binding.newsTitleTextView.text = article.title
        binding.newsDescriptionTextView.text = article.description

        Picasso.get().load(article.urlToImage).into(binding.newsImageView)



        return binding.root
    }
}