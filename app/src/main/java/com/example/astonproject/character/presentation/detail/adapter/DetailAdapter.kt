package com.example.astonproject.character.presentation.detail.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonproject.databinding.CharacterEpisodeItemBinding
import com.example.astonproject.episode.domain.model.EpisodeResult

class DetailAdapter : ListAdapter<EpisodeResult, DetailViewHolder>(DetailDiffUtil()) {

    var onEpisodeClickListener: OnEpisodeClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = CharacterEpisodeItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val episodePosition = getItem(position)
        holder.bind(episodePosition)
        holder.itemView.setOnClickListener {
            onEpisodeClickListener?.onEpisodeClick(episodePosition)
        }
    }

    interface OnEpisodeClickListener {
        fun onEpisodeClick(result: EpisodeResult)
    }
}