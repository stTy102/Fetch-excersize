package com.example.fetch

import androidx.compose.runtime.mutableStateOf
import com.example.fetch.network.ApiHelper
import com.example.fetch.network.Item
import com.example.fetch.ui.model.UiModel
import com.example.fetch.viewmodel.MainScreenViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    lateinit var viewModel: MainScreenViewModel

    @MockK(relaxed = true)
    lateinit var apiHelper: ApiHelper

    private val defaultValue = mutableStateOf(false)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainScreenViewModel(apiHelper)

    }

    @Test
    fun `api returned error and user hit retry then we show loading again`() {
        viewModel.retryApi()
        assertEquals(viewModel.uiState.value , MainScreenViewModel.UiState.Loading)
    }

    @Test
    fun `testing transform function to ui model`() {
        val result = transformToUiModel(provideMockData())
        assertEquals(result, finalResult2())
    }

    private fun transformToUiModel(items: List<Item>) =
        items.filter { item -> !item.name.isNullOrEmpty() }.sortedBy { item -> item.listId }.groupBy {
            it.listId
        }.map {
            UiModel(it.key, it.value.sortedBy { item -> item.id }, defaultValue)
        }


    private fun finalResult2() = listOf(
        UiModel(
            1,
            listOf(
                Item(
                    listId = 1,
                    name = "item name 1",
                    id = 1
                ),
                Item(
                    listId = 1,
                    name = "item name 3",
                    id = 3
                )
            ),
            defaultValue
        ),
        UiModel(
            2,
            listOf(
                Item(
                    listId = 2,
                    name = "item name 2",
                    id = 2
                )
            ),
            defaultValue
        )
    )

    private fun provideMockData() = listOf(
        Item(
            listId = 1,
            name = "item name 3",
            id = 3
        ),
        Item(
            listId = 2,
            name = "item name 2",
            id = 2
        ),
        Item(
            listId = 1,
            name = "item name 1",
            id = 1
        ),
        Item(
            listId = 1,
            name = null,
            id = 4
        ),
        Item(
            listId = 2,
            name = "",
            id = 5
        )

    )


}
