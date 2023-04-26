package com.example.astonproject.character.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.character.domain.model.CharacterResult

class CharacterDiffUtil() : DiffUtil.ItemCallback<CharacterResult>() {
    override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return oldItem == newItem
    }
}