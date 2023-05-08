package com.example.astonproject.episode.presentation.episode.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.episode.domain.model.EpisodeResult

class EpisodeDiffUtil : DiffUtil.ItemCallback<EpisodeResult>() {

    override fun areItemsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return oldItem == newItem
    }
}