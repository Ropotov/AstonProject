package com.example.astonproject.character.presentation.detail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.databinding.CharacterEpisodeItemBinding
import com.example.astonproject.episode.domain.model.EpisodeResult

class DetailViewHolder(val binding: CharacterEpisodeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind( position: EpisodeResult){
            binding.textView.text = position.name
        }
}
