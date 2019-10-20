package com.example.epicture

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_upload.*
import android.R.attr.bitmap
import android.app.DownloadManager
import android.app.VoiceInteractor
import kotlinx.android.synthetic.main.activity_upload.navfav
import kotlinx.android.synthetic.main.activity_upload.navhome

import kotlinx.android.synthetic.main.activity_upload.navsearch
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.text.StringBuilder


class Upload : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        navhome.setOnClickListener {
            switchActivity(Home::class.java)
        }
        navsearch.setOnClickListener {
            switchActivity(Search::class.java)
        }
        navfav.setOnClickListener {
            switchActivity(Favorite::class.java)
        }

        img_pick_btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                } else
                    pickImage()
            } else
                pickImage()
        }

    }

    private fun switchActivity(act: Class<*>?) {
        val intent = Intent(this, act)
        intent.putExtra("accessToken", getIntent().getStringExtra("accessToken"))
        intent.putExtra("Username", getIntent().getStringExtra("Username"))
        intent.putExtra("accountId", getIntent().getStringExtra("accountId"))
        intent.putExtra("refreshToken", getIntent().getStringExtra("refreshToken"))
        this.startActivity(intent)
        this.finish()
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImage()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)

            var uri: Uri = data!!.data

            println("OK1")
            println(data)

            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            println("OK2")
            println(bitmap.toString())
            }
        val test = Base64.getEncoder().encodeToString(R.id.image_view.toString().toByteArray())
        println(test)


        val queue = Volley.newRequestQueue(this)

        //Launch query API
        val url = "https://api.imgur.com/3/upload/"+test+"&client_id=f424c0c044998c8"


        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                println("${response}")
            },
            Response.ErrorListener {})
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${getIntent().getStringExtra("accessToken")}"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

        super.onActivityResult(requestCode, resultCode, data)
    }

}
