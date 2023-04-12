package com.example.astonproject.presentation.screens.characterFragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astonproject.databinding.CharacterItemBinding
import com.example.astonproject.domain.model.character.CharacterResult

class CharacterViewHolder(val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(position: CharacterResult?) {
        with(binding) {
            tvCharacterName.text = position?.name
            tvCharacterSpecies.text = "Species: ${position?.species}"
            tvCharacterGender.text = "Gender: ${position?.gender}"
            tvCharacterStatus.text = "Status: ${position?.status}"
            Glide
                .with(ivCharacterPhoto)
                .load(position?.image)
                .centerCrop()
                .into(ivCharacterPhoto)
        }
    }
}