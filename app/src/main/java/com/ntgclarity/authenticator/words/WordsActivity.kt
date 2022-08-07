package com.ntgclarity.authenticator.words

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
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

// https://newsapi.org/v2/everything?q=tesla&from=2022-07-07&sortBy=publishedAt&apiKey=2f956d41803f48fdaa4772e30c68deea

class WordsActivity : AppCompatActivity(), Callback<NewsClass?> {
    var adapter: WordsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        //val layoutManager = LinearLayoutManager(this)
        //val layoutManager = GridLayoutManager(this, 2)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, 1)


        val words = generateNews()
        adapter = WordsAdapter(words)
        val rvWords: RecyclerView = findViewById(R.id.rv_words)

        rvWords.layoutManager = staggeredGridLayoutManager
        rvWords.adapter = adapter
        getNews()
    }

    private fun getNews() {
        service.everything("Tesla", "2f956d41803f48fdaa4772e30c68deea")?.enqueue(this)
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
        TODO("Not yet implemented")
    }
}