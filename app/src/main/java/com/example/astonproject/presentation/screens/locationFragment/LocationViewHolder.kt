package com.example.astonproject.presentation.screens.locationFragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astonproject.databinding.CharacterItemBinding
import com.example.astonproject.databinding.LocationItemBinding
import com.example.astonproject.domain.model.character.CharacterResult
import com.example.astonproject.domain.model.location.LocationResult

class LocationViewHolder(val binding: LocationItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(position: LocationResult?) {
        with(binding) {
            tvLocationName.text = position?.name
            tvLocationDimension.text = "Dimension: ${position?.dimension}"
            tvLocationType.text = "Type: ${position?.type}"
        }
    }
}