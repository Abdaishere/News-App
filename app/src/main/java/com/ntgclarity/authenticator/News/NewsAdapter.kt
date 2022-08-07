package com.ntgclarity.authenticator.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.example.Articles
import com.ntgclarity.authenticator.R

class NewsAdapter(
    var article: Array<Articles>?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<NewsAdapter.WordViewHolder>() {

    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val tvWord: TextView
        val ivPhoto: ImageView

        init {
            tvWord = view.findViewById(R.id.tv_word)
            ivPhoto = view.findViewById(R.id.iv_photo)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_word, parent, false)

        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = article?.get(position)

        holder.tvWord.text = word?.title

        Glide.with(holder.itemView)
            .load(word?.urlToImage)
            .into(holder.ivPhoto)
    }

    override fun getItemCount(): Int = article?.size ?: 0
}


data class Word(val word: String, val url: String)