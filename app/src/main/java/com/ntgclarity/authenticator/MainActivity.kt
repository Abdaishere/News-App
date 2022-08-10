package com.ntgclarity.authenticator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ntgclarity.authenticator.News.NewsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegistration = findViewById<Button>(R.id.btn_register)

        // TODO add langue to the top right of the screen arabic and english
        // TODO add a place to chose what topic to chose from in the api

        // TODO add map API to locate the person or chose where the news come from
        btnRegistration.setOnClickListener {
            startRegistration()
        }

        btnLogin.setOnClickListener {
            startWords()
        }
    }

    fun startRegistration() {
        val intent = Intent(this, RegistrationActivity::class.java)

        startActivity(intent)
    }
    fun startWords() {
        val intent = Intent(this, NewsActivity::class.java)

        startActivity(intent)
    }
}