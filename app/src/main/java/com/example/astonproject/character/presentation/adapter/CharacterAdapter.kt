package com.example.astonproject.character.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.astonproject.databinding.CharacterItemBinding
import com.example.astonproject.character.domain.model.CharacterResult

class CharacterAdapter : PagingDataAdapter<CharacterResult, CharacterViewHolder>(CharacterDiffUtil()) {

    var onCharacterClickListener: ((CharacterResult?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterPosition = getItem(position)
        holder.bind(characterPosition)
        holder.binding.root.setOnClickListener {
            onCharacterClickListener?.invoke(characterPosition)
        }
    }
}