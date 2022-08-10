package com.ntgclarity.authenticator.News

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.example.Articles
import com.example.example.NewsClass
import com.maximeroussy.invitrode.WordGenerator
import com.ntgclarity.authenticator.Api.service
import com.ntgclarity.authenticator.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.tourism.Adapter.CenterZoomLayoutManager

class NewsActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener, Callback<NewsClass?> {
    // https://newsapi.org/
    val Topic = "Tesla"
    val ApiKey = "2f956d41803f48fdaa4772e30c68deea"

    var adapter: NewsAdapter? = null
    var News = generateNews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.swipe_view)
//
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, 1)
//
//        adapter = NewsAdapter(News, this)
//        val rvNews: RecyclerView = findViewById(R.id.rv_article)
//
//        rvNews.layoutManager = staggeredGridLayoutManager
//        rvNews.adapter = adapter
//        getNews()

        // init
        val articleRV = findViewById<RecyclerView>(R.id.rv_article)

        val layoutManager = CenterZoomLayoutManager(context = this)

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        articleRV.layoutManager = layoutManager

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(articleRV)
        articleRV.isNestedScrollingEnabled = false

        // add items to array list
        adapter = NewsAdapter(News, this)
        getNews()

        articleRV.adapter = adapter

    }

    private fun getNews() {
        service.everything(Topic, ApiKey)?.enqueue(this)
    }

    private fun generateNews(): Array<Articles> {
        val generator = WordGenerator()

        return Array(100) {
            Articles(
                null,
                generator.newWord(10),
                generator.newWord(10),
                generator.newWord(10),
                "https://picsum.photos/100/200",
                generator.newWord(10),
                generator.newWord(10),
                generator.newWord(10)
            )
        }
    }

    override fun onResponse(call: Call<NewsClass?>, response: Response<NewsClass?>) {
        adapter?.article = response.body()?.articles
        adapter?.notifyDataSetChanged()
    }

    override fun onFailure(call: Call<NewsClass?>, t: Throwable) {
        Toast.makeText(this, "Error. Please try again later", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        val clicked = adapter?.article?.get(position)
        if (clicked != null) {
            Toast.makeText(this, "${clicked.title} clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW).setData(clicked?.url?.toUri()))
        }
    }
}