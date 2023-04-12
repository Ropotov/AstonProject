package com.example.astonproject.presentation.screens.characterFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.astonproject.databinding.CharacterItemBinding
import com.example.astonproject.domain.model.character.Result

class CharacterAdapter : PagingDataAdapter<Result, CharacterViewHolder>(DiffUtilsCallback()) {

    var onCharacterClickListener: ((Result?) -> Unit)? = null

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