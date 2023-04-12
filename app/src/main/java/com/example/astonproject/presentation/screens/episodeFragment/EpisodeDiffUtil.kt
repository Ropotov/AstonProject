package com.example.astonproject.presentation.screens.episodeFragment

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.domain.model.episode.EpisodeResult

class EpisodeDiffUtil() : DiffUtil.ItemCallback<EpisodeResult>() {

    override fun areItemsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return oldItem == newItem
    }
}