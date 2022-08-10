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
            findViewById<EditText>(R.id.et_Location)?.setText(info.get("location").toString())
        } else {
            findViewById<EditText>(R.id.et_category)?.setText("health")
            findViewById<EditText>(R.id.et_Location)?.setText("Egypt")
        }

        val btnNews = findViewById<Button>(R.id.btn_News)
        val btnLocation = findViewById<Button>(R.id.btn_Location)

        btnLocation.setOnClickListener {
            chooseLocation()
        }

        btnNews.setOnClickListener {
            showNews()
        }
    }

    private fun chooseLocation() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun showNews() {
        val intent = Intent(this, NewsActivity::class.java)

        val info = Bundle()
        info.putString("category", findViewById<EditText>(R.id.et_category)?.text.toString())
        info.putString("location", findViewById<EditText>(R.id.et_Location)?.text.toString())
        intent.putExtras(info)

        startActivity(intent)

    }
}