package com.example.astonproject.location.presentation.detail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.databinding.CharacterEpisodeItemBinding

class LocationDetailViewHolder(val binding: CharacterEpisodeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind( position: CharacterResult){
            binding.textView.text = position.name
        }
}
