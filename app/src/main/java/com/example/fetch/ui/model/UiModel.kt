package com.example.fetch.ui.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.fetch.network.Item

data class UiModel(
    val listId: Int,
    val items: List<Item>,
    val isExpanded: MutableState<Boolean> = mutableStateOf(false)
)
