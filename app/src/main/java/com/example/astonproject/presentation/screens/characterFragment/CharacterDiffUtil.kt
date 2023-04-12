package com.example.astonproject.presentation.screens.characterFragment

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.domain.model.character.CharacterResult

class CharacterDiffUtil() : DiffUtil.ItemCallback<CharacterResult>() {
    override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return oldItem == newItem
    }
}