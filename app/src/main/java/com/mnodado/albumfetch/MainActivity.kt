package com.mnodado.albumfetch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject


//import

class MainActivity : AppCompatActivity() {

    private lateinit var trackList: ArrayList<Track>
    private lateinit var rvTracks: RecyclerView
    private lateinit var adapter: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trackList = ArrayList<Track>()
        rvTracks = findViewById(R.id.rvTracks)
        setupRecyclerView()
        fetchTracks("C418", "72 Minutes of Fame")
    }

    private fun setupRecyclerView() {
        this.adapter = TrackAdapter(trackList)
        rvTracks.adapter = this.adapter
        rvTracks.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun fetchTracks(artist: String, album: String) {
        val apikey = BuildConfig.API_KEY
        val client = AsyncHttpClient()
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

                val responseObject = json?.jsonObject
                val album: JSONObject? = responseObject?.getJSONObject("album")

                if (album != null) {
                    val albumArtUrl = album.getJSONArray("image").getJSONObject(1).getString("#text")
                    val tracksJsonArray: JSONArray = album.getJSONObject("tracks").getJSONArray("track")
                    trackList.clear()
                    val tracks = Track.fromJson(tracksJsonArray, albumArtUrl)

                    trackList.addAll(tracks)
                    this@MainActivity.adapter.notifyDataSetChanged()

                }
            }
        }]
    }
}