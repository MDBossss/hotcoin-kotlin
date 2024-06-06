package android.tvz.hr.hotcoin.ui.news

import android.content.Context
import android.content.Intent
import android.tvz.hr.hotcoin.R
import android.tvz.hr.hotcoin.model.Article
import android.tvz.hr.hotcoin.ui.news_details.NewsDetailsFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(private val context: Context, private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.news_title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.news_description_text_view)
        val sourceTextView: TextView = itemView.findViewById(R.id.news_source_text_view)
        val imageView: ImageView = itemView.findViewById(R.id.news_image_view)


        init{
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val article = articles[position]

                    // Open NewsDetailsFragment
                    val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(article)
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
        holder.sourceTextView.text = article.source.name
        Picasso.get()
            .load(article.urlToImage)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}