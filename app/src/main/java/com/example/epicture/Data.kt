package com.example.epicture

import android.graphics.Movie
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment

data class Data (
    val accessToken: String,
    val refreshToken: String,
    val accountId: String,
    val Username: String
)

class Photo (
    val id: String,
    val title: String
)
