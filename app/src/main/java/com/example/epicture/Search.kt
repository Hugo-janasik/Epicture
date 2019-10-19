package com.example.epicture

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.navfav
import kotlinx.android.synthetic.main.activity_search.navsearch
import kotlinx.android.synthetic.main.activity_search.navupload
import kotlinx.android.synthetic.main.activity_search.next
import kotlinx.android.synthetic.main.activity_search.photo1
import kotlinx.android.synthetic.main.activity_search.photo2
import kotlinx.android.synthetic.main.activity_search.photo3
import kotlinx.android.synthetic.main.activity_search.prev
import org.json.JSONArray
import java.util.ArrayList

class Search : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        navhome.setOnClickListener {
            switchActivity(Home::class.java)
        }
        navupload.setOnClickListener {
            switchActivity(Upload::class.java)
        }
        navfav.setOnClickListener {
            switchActivity(Favorite::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu!!.findItem(R.id.search)
        var searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search View Hint"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                val queue = Volley.newRequestQueue(this@Search)

                //Launch query API
                val url = "https://api.imgur.com/3/gallery/search/?q="+query+"&client_id=f424c0c044998c8"

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        var images: JSONArray = response.getJSONArray("data")
                        var photos: ArrayList<Photo> = ArrayList<Photo>()

                        for (i in 0 until images.length()) {
                            val item = images.getJSONObject((i))
                            if (item.getBoolean("is_album")) {
                                var photo: Photo = Photo(item.getString("cover"), item.getString("title"))
                                photos.add(photo)
                            } else {
                                var photo: Photo = Photo(item.getString("id"), item.getString("title"))
                                photos.add(photo)
                            }
                        }

                        if (photos.size == 0) {
                            textView.text = "Nothing to display"
                            photo1.setImageResource(android.R.color.transparent)
                            photo2.setImageResource(android.R.color.transparent)
                            photo3.setImageResource(android.R.color.transparent)
                        } else
                            textView.text = ""

                        var iterator: Int = 0
                        if (photos.size > 0)
                            Picasso.with(this@Search).load("https://i.imgur.com/" + photos[iterator].id + ".jpg").into(photo1)
                        if (photos.size > 1)
                            Picasso.with(this@Search).load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg").into(photo2)
                        if (photos.size > 2)
                            Picasso.with(this@Search).load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg").into(photo3)

                        next.setOnClickListener {
                            if (photos.size - iterator > 3) {
                                iterator += 3
                                Picasso.with(this@Search)
                                    .load("https://i.imgur.com/" + photos[iterator].id + ".jpg").into(photo1)
                                Picasso.with(this@Search)
                                    .load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg")
                                    .into(photo2)
                                Picasso.with(this@Search)
                                    .load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg")
                                    .into(photo3)
                            }
                        }
                        prev.setOnClickListener {
                            if (iterator >= 3) {
                                iterator -= 3
                                Picasso.with(this@Search)
                                    .load("https://i.imgur.com/" + photos[iterator].id + ".jpg")
                                    .into(photo1)
                                Picasso.with(this@Search)
                                    .load("https://i.imgur.com/" + photos[iterator + 1].id + ".jpg")
                                    .into(photo2)
                                Picasso.with(this@Search)
                                    .load("https://i.imgur.com/" + photos[iterator + 2].id + ".jpg")
                                    .into(photo3)
                            }
                        }
                    },
                    Response.ErrorListener {})

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest)
                return false
            }

        })
        return true
    }

}