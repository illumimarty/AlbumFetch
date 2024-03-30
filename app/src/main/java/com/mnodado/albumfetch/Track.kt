package com.mnodado.albumfetch

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class Track() {

    lateinit var imageUrl: String
    lateinit var trackCount: String
    lateinit var releaseDate: String
    lateinit var albumTitle: String

    public
    companion object {

        fun fromJson(arr: JSONArray): ArrayList<Track> {
            val tracks = ArrayList<Track>(arr.length())

            for (i in 0..<arr.length()) {

                try {
                    val trackObj = arr.getJSONObject(i)
                    val track = Track.fromJson(trackObj)
                    if (track != null) {
                        tracks.add(track)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return tracks
        }

        fun fromJson(obj: JSONObject): Track? {
            var newTrack = Track()

            try {
                newTrack.albumTitle = obj.getString("name")
//                newTrack.releaseDate = obj.getString("releasedate")
                newTrack.releaseDate = obj.getString("name")

                newTrack.imageUrl = obj.getString("name")
                newTrack.trackCount = obj.getString("name")
            } catch (error: JSONException) {
                error.printStackTrace()
                return null
            }

            return newTrack
        }
    }
}