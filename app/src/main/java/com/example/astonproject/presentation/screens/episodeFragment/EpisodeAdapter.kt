package com.example.astonproject.presentation.screens.episodeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.astonproject.databinding.EpisodeItemBinding
import com.example.astonproject.domain.model.episode.EpisodeResult


class EpisodeAdapter : PagingDataAdapter<EpisodeResult, EpisodeViewHolder>(EpisodeDiffUtil()) {

    var onCharacterClickListener: ((EpisodeResult?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val locationPosition = getItem(position)
        holder.bind(locationPosition)
        holder.binding.root.setOnClickListener {
            onCharacterClickListener?.invoke(locationPosition)
        }
    }
}