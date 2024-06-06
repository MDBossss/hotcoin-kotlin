package android.tvz.hr.hotcoin.ui.bookmarked

import android.content.Context
import android.tvz.hr.hotcoin.R
import android.tvz.hr.hotcoin.model.Article
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BookmarkedAdapter(private val onItemClickListener: OnItemClickListener,  val articles: List<Article>) :
    RecyclerView.Adapter<BookmarkedAdapter.BookmarkedViewHolder>() {

        interface OnItemClickListener{
            fun onDeleteButtonClick(article:Article)
        }

    inner class BookmarkedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.bookmarked_news_title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.bookmarked_news_description_text_view)
        val sourceTextView: TextView = itemView.findViewById(R.id.bookmarked_news_source_text_view)
        val imageView: ImageView = itemView.findViewById(R.id.bookmarked_news_image_view)
        val unbookmarkButton: Button = itemView.findViewById(R.id.unbookmark_button)


        init{
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val article = articles[position]

                    //Open NewsDetailsFragment
                    val action = BookmarkedFragmentDirections.actionBookmarkedFragmentToNewsDetailsFragment(article)
                    itemView.findNavController().navigate(action)
                }
            }

            unbookmarkButton.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val article = articles[position]

                    onItemClickListener.onDeleteButtonClick(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkedViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmarked_news_item, parent, false)
        return BookmarkedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookmarkedViewHolder, position: Int) {
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
