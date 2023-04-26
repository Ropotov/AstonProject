package com.example.astonproject.location.presentation.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.databinding.LocationItemBinding
import com.example.astonproject.location.domain.model.LocationResult

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