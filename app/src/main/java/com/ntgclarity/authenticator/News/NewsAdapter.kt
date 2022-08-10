package com.ntgclarity.authenticator.News


import android.widget.ImageView
import android.widget.TextView
import com.example.example.Articles
import com.ntgclarity.authenticator.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(
    var article: Array<Articles>?,
    private val listener: OnItemClickListener,
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val tvWord: TextView
        val tvdescription: TextView
        val tvauthor: TextView
        val ivPhoto: ImageView

        init {
            tvWord = view.findViewById(R.id.tv_word)
            tvauthor = view.findViewById(R.id.article_author)
            ivPhoto = view.findViewById(R.id.iv_photo)
            tvdescription = view.findViewById(R.id.article_description)

            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_word, parent, false)

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = article?.get(position)

        // image
        Picasso.get().load(article?.urlToImage).into(holder.ivPhoto)

        // start animation
        holder.ivPhoto.animate()
        holder.tvdescription.text = article?.description
        holder.tvWord.text = article?.title
        holder.tvauthor.text = "By " + article?.author


    }

    override fun getItemCount(): Int = article?.size ?: 0
}


data class Word(val word: String, val url: String)