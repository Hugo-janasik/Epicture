package com.example.epicture

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class Search : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
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

                println("QUERY = "+query)
                println("URL = "+url
                )

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        // Display the first 500 characters of the response string.
                        println("${response}")
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

/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
 inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        var searchItem: MenuItem = menu.findItem(R.id.search)

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Launch query API
                val queue = Volley.newRequestQueue(activity)

                val url = "https://api.imgur.com/3/gallery/search/?q="+query+"&client_id=f424c0c044998c8"

                println("QUERY = "+query)
                println("URL = "+url
                )

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                // Display the first 500 characters of the response string.
                        if (text_search != null)
                            text_search.text = "Response is: ${response}"
                        else
                            text_search.text = "oui"
            },
            Response.ErrorListener { error -> text_search.text = error.message })
        // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest)
                return false
            }
 */