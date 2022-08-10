package com.ntgclarity.authenticator.News

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import authenticator.R
import com.example.example.Articles
import com.example.example.NewsClass
import com.ntgclarity.authenticator.api.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.tourism.Adapter.CenterZoomLayoutManager
import java.util.*

class NewsActivity : AppCompatActivity(),
    NewsAdapter.OnItemClickListener, Callback<NewsClass?> {

    private var apikey: String = ""

    private var adapter: NewsAdapter? = null
    private var News = generateNews()

    private var Category = ""
    private var Location = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        supportActionBar?.hide()

        setContentView(R.layout.swipe_view)


        // Gather Data
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.news.api"]
        apikey = value.toString()

        val info = intent.extras
        Category = info?.get("category").toString()
        Location = info?.get("location").toString()

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
        val code = getCountryCode(Location) ?: ""
        println("Searching about $Category")
        println(code)

        service.top(Category, code, apikey)?.enqueue(this)

    }

    private fun generateNews(): Array<Articles> {
        var imgwidth = 1280
        var imgheigh = 720

        return Array(200) {
            Articles(
                null,
                "Unknown",
                "Error 404",
                "Please choose different location or try again later!!!",
                "Error 404",
                "https://picsum.photos/${imgwidth++}/${imgheigh++}",
                "Error 404",
                "Error 404"
            )
        }
    }

    override fun onResponse(call: Call<NewsClass?>, response: Response<NewsClass?>) {
        if (response.body()?.articles?.isEmpty() == true) {
            findViewById<TextView>(R.id.textView).setText("Not Found")
            return
        }

        adapter?.article = response.body()?.articles

        var title = Category
        if (title.isEmpty())
            title = Location

        findViewById<TextView>(R.id.textView).setText("Top in ${title}")
        adapter?.notifyDataSetChanged()
    }

    override fun onFailure(call: Call<NewsClass?>, t: Throwable) {
        Toast.makeText(this, "Error. Please try again later", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        val clicked = adapter?.article?.get(position)
        if (clicked != null) {
            Toast.makeText(this, "Opening on \"${clicked.source?.name}\"", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(Intent.ACTION_VIEW).setData(clicked.url?.toUri()))
        }
    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry.equals(countryName, true) }
}