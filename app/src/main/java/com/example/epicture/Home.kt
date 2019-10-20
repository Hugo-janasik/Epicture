package com.example.epicture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONArray
import java.util.*
import android.os.Bundle
import com.squareup.picasso.Picasso

class Home : AppCompatActivity() {

    fun switchActivity(act: Class<*>?) {
        val intent = Intent(this, act)
        intent.putExtra("accessToken", getIntent().getStringExtra("accessToken"))
        intent.putExtra("Username", getIntent().getStringExtra("Username"))
        intent.putExtra("accountId", getIntent().getStringExtra("accountId"))
        intent.putExtra("refreshToken", getIntent().getStringExtra("refreshToken"))
        this.startActivity(intent)
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val url = "https://api.imgur.com/3/account/me/images"
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
                        Picasso.with(this)
                            .load("https://i.imgur.com/" + photos[iterator].id + ".jpg").into(photo1)
                        Picasso.with(this)
                            .load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg")
                            .into(photo2)
                        Picasso.with(this)
                            .load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg")
                            .into(photo3)
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

        navsearch.setOnClickListener {
            switchActivity(Search::class.java)
        }
        navupload.setOnClickListener {
            switchActivity(Upload::class.java)
        }
        navfav.setOnClickListener {
            switchActivity(Favorite::class.java)
        }
        queue.add(jsonObjectRequest)
    }
}