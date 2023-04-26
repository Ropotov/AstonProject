package com.example.astonproject.location.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.astonproject.databinding.LocationItemBinding
import com.example.astonproject.location.domain.model.LocationResult


class LocationAdapter : PagingDataAdapter<LocationResult, LocationViewHolder>(LocationDiffUtil()) {

    var onCharacterClickListener: ((LocationResult?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val locationPosition = getItem(position)
        holder.bind(locationPosition)
        holder.binding.root.setOnClickListener {
            onCharacterClickListener?.invoke(locationPosition)
        }
    }
}