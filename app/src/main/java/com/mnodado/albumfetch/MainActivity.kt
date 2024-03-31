package com.mnodado.albumfetch
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var trackList: ArrayList<Track>
    private lateinit var rvTracks: RecyclerView
    private lateinit var adapter: TrackAdapter
    private lateinit var albumArtView: ImageView
    private lateinit var albumNameTextField: TextInputLayout
    private lateinit var artistNameTextField: TextInputLayout
    private lateinit var fetchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMainActivity()
        setupRecyclerView()
        fetchTracks("Daft Punk", "Random Access Memories")
    }

    private fun setupMainActivity() {
        trackList = ArrayList<Track>()
        rvTracks = findViewById(R.id.rvTracks)
        albumArtView = findViewById(R.id.albumArt)
        albumNameTextField = findViewById(R.id.albumField)
        artistNameTextField = findViewById(R.id.artistField)

        fetchButton = findViewById(R.id.fetchButton)
        fetchButton.setOnClickListener {
            val albumName = albumNameTextField.editText?.text.toString()
            val artistName = artistNameTextField.editText?.text.toString()

            if (albumName.isEmpty() or artistName.isEmpty()) {
                val text = "Enter album and artist name before fetching."
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this@MainActivity, text, duration)
                toast.show()
            } else {
                fetchTracks(artistName, albumName)
            }
        }
    }

    private fun setContent(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(albumArtView)
    }

    private fun setupRecyclerView() {
        this.adapter = TrackAdapter(trackList)
        rvTracks.adapter = this.adapter
        rvTracks.layoutManager = LinearLayoutManager(this@MainActivity)
        val dividerItemDecoration = DividerItemDecoration(rvTracks.context, LinearLayoutManager.VERTICAL)
        rvTracks.addItemDecoration(dividerItemDecoration)
    }

    private fun isJsonResponseValid(json: JsonHttpResponseHandler.JSON): JSONArray? {
        val responseObject = json?.jsonObject
        val album: JSONObject? = responseObject?.getJSONObject("album")

        // Check if album object exists
        if (album != null) {
            var trackJsonArray: JSONArray?
            // Check if track array exists
            try {
                trackJsonArray = album.getJSONObject("tracks").getJSONArray("track")
                return trackJsonArray
            } catch (e: Exception) {
                return null
            }
        }
        return null
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

                val tracksJsonArray = json?.let { isJsonResponseValid(it) }

                if (tracksJsonArray != null) {
                    // If tracks exist, populate MainActivity with API data
                    val albumArtUrl = json.jsonObject.getJSONObject("album").getJSONArray("image").getJSONObject(1).getString("#text")
                    trackList.clear()
                    val tracks = Track.fromJson(tracksJsonArray, albumArtUrl)

                    trackList.addAll(tracks)
                    this@MainActivity.adapter.notifyDataSetChanged()
                    setContent(albumArtUrl)
                } else {
                    // Else, Show Toast widget
                    val text = "Album not found, check your album/artist spelling and try again."
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(this@MainActivity, text, duration)
                    toast.show()
                }
            }
        }]
    }
}