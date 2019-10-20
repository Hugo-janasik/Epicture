package com.example.epicture

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
