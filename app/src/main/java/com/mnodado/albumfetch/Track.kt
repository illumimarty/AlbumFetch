package com.mnodado.albumfetch

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class Track() {

    var imageUrl: String? = null
    lateinit var trackCount: String
    lateinit var duration: String
    lateinit var trackTitle: String

    public
    companion object {

        fun fromJson(arr: JSONArray, imageUrl: String?): ArrayList<Track> {
            val tracks = ArrayList<Track>(arr.length())

            for (i in 0..<arr.length()) {

                try {
                    val trackObj = arr.getJSONObject(i)
                    val track = Track.fromJson(trackObj)

                    if (track != null) {
                        if (imageUrl != null) {
                            track.imageUrl = imageUrl
                        }
                        tracks.add(track)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return tracks
        }

        fun formatDuration(inDuration: String): String {
            // Convert input duration string to integer
            val durationInSeconds = inDuration.toIntOrNull() ?: 0

            // Calculate minutes and seconds
            val minutes = durationInSeconds / 60
            val seconds = durationInSeconds % 60

            // Format minutes and seconds
            val formattedMinutes = String.format("%02d", minutes)
            val formattedSeconds = String.format("%02d", seconds)

            // Return formatted duration string
            return "$formattedMinutes:$formattedSeconds"
        }

        fun fromJson(obj: JSONObject): Track? {
            var newTrack = Track()

            try {
                newTrack.trackTitle = obj.getString("name")

                val duration = formatDuration(obj.getString("duration"))
                newTrack.duration = duration

                newTrack.trackCount = obj.getJSONObject("@attr").getString("rank")
            } catch (error: JSONException) {
                error.printStackTrace()
                return null
            }

            return newTrack
        }
    }
}