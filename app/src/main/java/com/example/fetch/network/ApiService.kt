package com.example.fetch.network

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun fetchItems() : List<Item>
}
