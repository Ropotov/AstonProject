package com.example.astonproject.character.presentation.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.episode.domain.model.EpisodeResult

class DetailDiffUtil : DiffUtil.ItemCallback<EpisodeResult>() {
    override fun areItemsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return newItem.name == oldItem.name
    }

    override fun areContentsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return false
    }
}