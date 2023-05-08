package com.example.astonproject.episode.presentation.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.character.domain.model.CharacterResult

class EpisodeDetailDiffUtil : DiffUtil.ItemCallback<CharacterResult>() {

    override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return false
    }

}