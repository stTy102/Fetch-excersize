package com.example.fetch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fetch.R
import com.example.fetch.ui.components.CenterLoading
import com.example.fetch.ui.components.ErrorUi
import com.example.fetch.ui.components.ExpandableList
import com.example.fetch.ui.components.FetchHeader
import com.example.fetch.ui.theme.ToolbarHeight
import com.example.fetch.viewmodel.MainScreenViewModel


@Composable
fun MainScreen(viewModel: MainScreenViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(ToolbarHeight)
            .background(color = MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.Center) {
            FetchHeader(text = stringResource(id = R.string.app_name))
        }
        when(val ui = state.value) {
            is MainScreenViewModel.UiState.Loading -> {
                CenterLoading()
            }
            is MainScreenViewModel.UiState.Success -> {
                ExpandableList(items = ui.uiData) {
                    ui.uiData.find { item -> it == item.listId }?.let {
                        it.isExpanded.value = !it.isExpanded.value
                    }
                }
            }
            is MainScreenViewModel.UiState.Error -> {
                ErrorUi(text = stringResource(id = R.string.api_error_message), buttonText = stringResource(id = R.string.retry)) {
                    viewModel.retryApi()
                }
            }
        }
    }
}
