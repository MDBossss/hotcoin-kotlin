package android.tvz.hr.hotcoin.ui.charts

import android.graphics.Color
import android.tvz.hr.hotcoin.R
import android.tvz.hr.hotcoin.model.Coin
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ChartsAdapter(private val coins: List<Coin>) :
    RecyclerView.Adapter<ChartsAdapter.ChartsViewHolder>() {

    inner class ChartsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val coinImage: ImageView = itemView.findViewById(R.id.coin_image)
        val coinTitle: TextView = itemView.findViewById(R.id.coin_name)
        val coinPercentage:TextView = itemView.findViewById(R.id.coin_percentage)
        val coinPrice: TextView = itemView.findViewById(R.id.coin_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.charts_item, parent, false)
        return ChartsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChartsViewHolder, position: Int) {
        val coin = coins[position]
        Picasso.get()
            .load(coin.image)
            .into(holder.coinImage)
        holder.coinTitle.text = coin.name
        holder.coinPrice.text = "$${coin.currentPrice.toString()}"

        holder.coinPercentage.text = "${String.format("%.2f",coin.priceChangePercentage24h)}%"

        // Dynamic styling based on the price change %
        if(coin.priceChangePercentage24h > 0){
            holder.coinPercentage.setTextColor(Color.rgb(0,128,0))
            holder.coinPercentage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_arrow_drop_up_24,0,0,0)
        }
        else{
            holder.coinPercentage.setTextColor(Color.rgb(255,0,0))
            holder.coinPercentage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_arrow_drop_down_24,0,0,0)
        }
    }

    override fun getItemCount(): Int {
        return coins.size
    }

}