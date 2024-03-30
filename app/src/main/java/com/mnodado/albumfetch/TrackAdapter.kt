package com.mnodado.albumfetch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter (private val mTracks: List<Track>): RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumArtView = itemView.findViewById<ImageView>(R.id.imageView)
        val albumTitleView = itemView.findViewById<TextView>(R.id.albumTitle)
        val releaseDateView = itemView.findViewById<TextView>(R.id.releaseDate)
        val trackCountView = itemView.findViewById<TextView>(R.id.trackCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val albumView = inflater.inflate(R.layout.item_track, parent, false)
        return ViewHolder(albumView)
    }

    override fun getItemCount(): Int {
        return mTracks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track: Track = mTracks[position]

        val nameTextView = holder.albumTitleView
        nameTextView.text = track.albumTitle

        val releaseDateView = holder.releaseDateView
        releaseDateView.text = track.releaseDate

        val trackCountView = holder.trackCountView
        trackCountView.text = "10"
    }

}