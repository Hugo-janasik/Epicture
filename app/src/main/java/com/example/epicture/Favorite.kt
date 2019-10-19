package com.example.epicture

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_favorite.*

class Favorite : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        navsearch.setOnClickListener {
            switchActivity(Search::class.java)
        }
        navupload.setOnClickListener {
            switchActivity(Upload::class.java)
        }
        navhome.setOnClickListener {
            switchActivity(Home::class.java)
        }

    }

    fun switchActivity(act: Class<*>?) {
        val intent = Intent(this, act)
        intent.putExtra("accessToken", getIntent().getStringExtra("accessToken"))
        intent.putExtra("Username", getIntent().getStringExtra("Username"))
        intent.putExtra("accountId", getIntent().getStringExtra("accountId"))
        intent.putExtra("refreshToken", getIntent().getStringExtra("refreshToken"))
        this.startActivity(intent)
        this.finish()
    }
}