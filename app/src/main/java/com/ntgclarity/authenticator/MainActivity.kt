package com.ntgclarity.authenticator


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import authenticator.R
import com.ntgclarity.authenticator.Map.MapsActivity
import com.ntgclarity.authenticator.News.NewsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val info = intent.extras
        if (info != null) {
            findViewById<EditText>(R.id.et_Location)?.setText(info?.get("location").toString())
        } else {
            findViewById<EditText>(authenticator.R.id.et_category)?.setText("health")
            findViewById<EditText>(authenticator.R.id.et_Location)?.setText("Egypt")
        }

        val btnNews = findViewById<Button>(authenticator.R.id.btn_News)
        val btnLocation = findViewById<Button>(authenticator.R.id.btn_Location)


        // TODO add language to the top right of the screen arabic and english
        // TODO add a place to choose what category to chose from in the api

        btnLocation.setOnClickListener {
            ChooseLocation()
        }

        btnNews.setOnClickListener {
            ShowNews()
        }
    }

    private fun ChooseLocation() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun ShowNews() {
        val intent = Intent(this, NewsActivity::class.java)

        val info = Bundle()
        info.putString("category", findViewById<EditText>(authenticator.R.id.et_category)?.text.toString())
        info.putString("location", findViewById<EditText>(authenticator.R.id.et_Location)?.text.toString())
        intent.putExtras(info)

        startActivity(intent)

    }
}