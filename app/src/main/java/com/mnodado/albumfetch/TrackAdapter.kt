package com.mnodado.albumfetch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrackAdapter (private val mTracks: List<Track>): RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumArtView = itemView.findViewById<ImageView>(R.id.imageView)
        val trackTitleView = itemView.findViewById<TextView>(R.id.trackTitle)
        val durationTextView = itemView.findViewById<TextView>(R.id.duration)
        val trackNumber = itemView.findViewById<TextView>(R.id.trackNumber)
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

        val nameTextView = holder.trackTitleView
        nameTextView.text = track.trackTitle

        val releaseDateView = holder.durationTextView
        releaseDateView.text = "Duration: ${track.duration}"

        val trackCountView = holder.trackNumber
        trackCountView.text = "Track #${track.trackCount}"

        Glide.with(holder.itemView)
            .load(track.imageUrl)
            .into(holder.albumArtView)
    }

}