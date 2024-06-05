package android.tvz.hr.hotcoin.ui.news

import android.tvz.hr.hotcoin.R
import android.tvz.hr.hotcoin.model.Article
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.news_title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.news_description_text_view)
        val imageView: ImageView = itemView.findViewById(R.id.news_image_view)
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
        Picasso.get()
            .load(article.urlToImage)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}