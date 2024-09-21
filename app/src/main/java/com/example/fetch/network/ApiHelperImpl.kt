package com.example.fetch.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelperImpl @Inject constructor(
    private val api: ApiService
): ApiHelper {
    override fun fetchItems(): Flow<List<Item>> = flow {
        emit(api.fetchItems())
    }

}
