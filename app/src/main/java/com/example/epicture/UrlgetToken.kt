package com.example.epicture

//import okhttp3.HttpUrl
import kotlin.collections.HashMap

class UrlToken {
    companion object {
        fun parseUrl(param: String): Map<String, String> {
            val values = param.split("&")
            val store = HashMap<String, String>()

            for (pair in values) {
                val splited = pair.split("=")
                store.put(splited[0], splited[1])
            }

            return store
        }

        fun getData(url: String): Data {
//            val string = HttpUrl.parse(url)!!.encodeFragment()

            val parameters = parseUrl(url)

            val accessToken = parameters["access_token"]!!
            val refreshToken = parameters["refresh_token"]!!
            val accountId = parameters["account_id"]!!
            val accountUsername = parameters["account_username"]!!

            return Data (
                accessToken,
                refreshToken,
                accountId,
                accountUsername
            )
        }
    }
}