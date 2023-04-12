package com.example.astonproject.presentation.screens.characterFragment

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astonproject.databinding.CharacterItemBinding
import com.example.astonproject.domain.model.character.Result

class CharacterViewHolder(val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Result?) {
        with(binding) {
            tvCharacterName.text = position?.name
            tvCharacterSpecies.text = position?.species
            tvCharacterGender.text = position?.gender
            tvCharacterStatus.text = position?.status
            Glide
                .with(ivCharacterPhoto)
                .load(position?.image)
                .centerCrop()
                .into(ivCharacterPhoto)
        }
    }
}