package com.example.fetch.network

import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun fetchItems(): Flow<List<Item>>
}
