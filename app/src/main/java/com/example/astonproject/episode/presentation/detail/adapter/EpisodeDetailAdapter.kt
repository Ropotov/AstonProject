package com.example.astonproject.episode.presentation.detail.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.databinding.CharacterEpisodeItemBinding

class EpisodeDetailAdapter :
    ListAdapter<CharacterResult, EpisodeDetailViewHolder>(EpisodeDetailDiffUtil()) {

    var onCharacterClickListener: ((CharacterResult?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailViewHolder {
        val binding = CharacterEpisodeItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return EpisodeDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeDetailViewHolder, position: Int) {
        val characterPosition = getItem(position)
        holder.bind(characterPosition)
        holder.binding.root.setOnClickListener {
            onCharacterClickListener?.invoke(characterPosition)
        }
    }
}