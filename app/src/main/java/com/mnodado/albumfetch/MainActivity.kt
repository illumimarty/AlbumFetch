package com.mnodado.albumfetch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers



//import

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APIManager.getAlbumTracksForArtist("Daft Punk", "Random Access Memories")
    }

}

class APIManager {
    companion object Instance {

        private val apikey = BuildConfig.API_KEY
        private val client = AsyncHttpClient()

        fun getAlbumTracksForArtist(artist: String, album: String) {
            val endpoint = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=${apikey}&artist=${artist}&album=${album}&format=json"
            val params = RequestParams()
            client[endpoint, params, object: JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.d("API Response", response.toString())
                }

                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                    Log.d("API Response", json.toString())
                }
            }]
        }
    }
}