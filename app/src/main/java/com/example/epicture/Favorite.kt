package com.example.epicture

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_favorite.*
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

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

        val url = "https://api.imgur.com/3/account/${getIntent().getStringExtra("Username")}/favorites/"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                var images: JSONArray = response.getJSONArray("data")
                var photos: ArrayList<Photo> = ArrayList<Photo>()

                for (i in 0 until images.length()) {
                    val item = images.getJSONObject((i))
                    var photo: Photo = Photo(item.getString("id"), item.getString("title"))
                    photos.add(photo)
                }
                var iterator: Int = 0
                if (photos.size > 0)
                    Picasso.with(this).load("https://i.imgur.com/" + photos[iterator].id + ".jpg").into(photo1)
                if (photos.size > 1)
                    Picasso.with(this).load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg").into(photo2)
                if (photos.size > 2)
                    Picasso.with(this).load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg").into(photo3)

                next.setOnClickListener {
                    if (photos.size - iterator > 3) {
                        iterator += 3
                        if (photos.size - iterator > 0)
                            Picasso.with(this).load("https://i.imgur.com/" + photos[iterator].id + ".jpg").into(photo1)
                        if (photos.size - iterator > 1)
                            Picasso.with(this).load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg").into(photo2)
                        if (photos.size - iterator > 2)
                            Picasso.with(this).load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg").into(photo3)
                    }
                }
                prev.setOnClickListener {
                    if (iterator >= 3) {
                        iterator -= 3
                        Picasso.with(this)
                            .load("https://i.imgur.com/" + photos[iterator].id + ".jpg")
                            .into(photo1)
                        Picasso.with(this)
                            .load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg")
                            .into(photo2)
                        Picasso.with(this)
                            .load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg")
                            .into(photo3)
                    }
                }
            },
            Response.ErrorListener { error -> println("ERROR ${error.message}") })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${getIntent().getStringExtra("accessToken")}"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
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