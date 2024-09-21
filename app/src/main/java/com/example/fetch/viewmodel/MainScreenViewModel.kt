package com.example.fetch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.network.ApiHelper
import com.example.fetch.network.Item
import com.example.fetch.ui.model.UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val apiHelper: ApiHelper
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            fetchData()
        }
    }

    fun retryApi() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            fetchData()
        }
    }

    private suspend fun fetchData() {
        apiHelper.fetchItems().map(::transformToUiModel).catch {
            _uiState.emit(UiState.Error)
        }.collect {
            Log.d(TAG, "list after filter : $it")
            _uiState.emit(UiState.Success(it))
        }
    }

    private fun transformToUiModel(items: List<Item>) =
        items.filter { item -> !item.name.isNullOrEmpty() }.sortedBy { item -> item.listId }.groupBy {
            it.listId
        }.map {
            UiModel(it.key, it.value.sortedBy { item -> item.id })
        }


    companion object {
        private val TAG = "MainViewModel"
    }

    sealed class UiState {
        data object Loading: UiState()

        data class Success(val uiData: List<UiModel>): UiState()

        data object Error: UiState()
    }

}
