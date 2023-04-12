package com.example.astonproject.presentation.screens.locationFragment

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.domain.model.character.CharacterResult
import com.example.astonproject.domain.model.location.LocationResult

class LocationDiffUtil() : DiffUtil.ItemCallback<LocationResult>() {

    override fun areItemsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
        return oldItem == newItem
    }
}