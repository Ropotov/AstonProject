package com.example.astonproject.location.presentation.detail.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.databinding.CharacterEpisodeItemBinding
import com.example.astonproject.episode.presentation.detail.adapter.LocationDetailDiffUtil

class LocationDetailAdapter : ListAdapter<CharacterResult, LocationDetailViewHolder>(LocationDetailDiffUtil()) {

    val onCharacterClickListener: ((CharacterResult?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDetailViewHolder {
        val binding = CharacterEpisodeItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return LocationDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationDetailViewHolder, position: Int) {
        val characterPosition = getItem(position)
        holder.bind(characterPosition)
        holder.binding.root.setOnClickListener {
            onCharacterClickListener?.invoke(characterPosition)
        }
    }
}