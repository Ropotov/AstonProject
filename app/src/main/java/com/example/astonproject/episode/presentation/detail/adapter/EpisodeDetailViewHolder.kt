package com.example.astonproject.episode.presentation.detail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.databinding.CharacterEpisodeItemBinding

class EpisodeDetailViewHolder(val binding: CharacterEpisodeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind( position: CharacterResult){
            binding.textView.text = position.name
        }
}
