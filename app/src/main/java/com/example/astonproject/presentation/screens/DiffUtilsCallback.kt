package com.example.astonproject.presentation.screens.characterFragment

import androidx.recyclerview.widget.DiffUtil
import com.example.astonproject.domain.model.character.Result

class DiffUtilsCallback() : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}