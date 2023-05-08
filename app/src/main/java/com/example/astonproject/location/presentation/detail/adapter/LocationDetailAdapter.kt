package com.example.astonproject.location.presentation.detail.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.databinding.CharacterEpisodeItemBinding

class LocationDetailAdapter :
    ListAdapter<CharacterResult, LocationDetailViewHolder>(LocationDetailDiffUtil()) {

    var onCharacterClickListener: OnCharacterClickListener? = null

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
        holder.itemView.setOnClickListener {
            onCharacterClickListener?.onCharacterClick(characterPosition)
        }
    }

    interface OnCharacterClickListener {
        fun onCharacterClick(result: CharacterResult)
    }
}