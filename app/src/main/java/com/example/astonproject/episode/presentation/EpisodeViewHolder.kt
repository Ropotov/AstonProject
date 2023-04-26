package com.example.astonproject.episode.presentation

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.databinding.EpisodeItemBinding
import com.example.astonproject.episode.domain.model.EpisodeResult

class EpisodeViewHolder(val binding: EpisodeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(position: EpisodeResult?) {
        with(binding) {
            tvEpisodeName.text = position?.name
            tvEpisodeDataAir.text = "Date: ${position?.air_date}"
            tvEpisodeNumber.text = position?.episode
        }
    }
}