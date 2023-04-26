package com.example.astonproject.location.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.location.domain.model.LocationResult

class LocationDiffUtil() : DiffUtil.ItemCallback<LocationResult>() {

    override fun areItemsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
        return oldItem == newItem
    }
}