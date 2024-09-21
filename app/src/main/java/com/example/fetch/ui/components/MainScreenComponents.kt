package com.example.fetch.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.fetch.R
import com.example.fetch.network.Item
import com.example.fetch.ui.model.UiModel
import com.example.fetch.ui.theme.ElevationAmount
import com.example.fetch.ui.theme.HeaderHeight
import com.example.fetch.ui.theme.LargeMargin
import com.example.fetch.ui.theme.NormalMargin
import com.example.fetch.ui.theme.ProgressSize
import com.example.fetch.ui.theme.Typography
import com.example.fetch.ui.theme.arrowUp

@Composable
fun RowScope.FetchHeader(text: String) {
    Text(
        text = text,
        style = Typography.headlineSmall,
        modifier = Modifier.align(Alignment.CenterVertically),
        textAlign = TextAlign.Center
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun HeaderItem(text: String, isExpanded: Boolean, modifier: Modifier = Modifier) {
    val transitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = !isExpanded
        }
    }
    val transition = rememberTransition(transitionState, label = "transition")

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "") {
        if (isExpanded) 0f else 180f
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(HeaderHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, modifier = Modifier.weight(1.0f))
        Icon(imageVector = arrowUp, contentDescription = "", Modifier.rotate(arrowRotationDegree))
    }
}

@Composable
private fun ListItem(name: String, modifier: Modifier = Modifier) {
    Text(text = name, modifier.padding(horizontal = LargeMargin))
}

@Composable
private fun ListHeader(text: String, id: Int, isExpanded: Boolean, onHeaderClick: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        Card(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = NormalMargin, vertical = NormalMargin)
                .clickable { onHeaderClick(id) },
            shape = RoundedCornerShape(NormalMargin),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(
                defaultElevation = ElevationAmount
            )
        ) {
            HeaderItem(
                text = text,
                isExpanded = isExpanded,
                modifier = Modifier.padding(horizontal = NormalMargin)
            )
        }
    }
}

@Composable
fun ExpandableList(items: List<UiModel>, onHeaderClick: (Int) -> Unit) {

    LazyColumn {
        items.onEach { entry ->
            expandableItem(
                header = entry.listId.toString(),
                data = entry.items,
                id = entry.listId,
                isExpanded = entry.isExpanded.value,
                onHeaderClick = onHeaderClick
            )
        }
    }
}

@Composable
fun CenterLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(ProgressSize)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ErrorUi(text: String, buttonText: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = text, color = MaterialTheme.colorScheme.error)
        Button(onClick = onClick, modifier = Modifier.padding(vertical = NormalMargin)) {
            Text(text = buttonText)
        }
    }
}


private fun LazyListScope.expandableItem(
    header: String, id: Int, data: List<Item>, isExpanded: Boolean, onHeaderClick: (Int) -> Unit
) {
    item {
        ListHeader(
            text = String.format(stringResource(id = R.string.list_item_name), header),
            isExpanded = isExpanded,
            id = id,
            onHeaderClick = onHeaderClick
        )
    }
    if (isExpanded) {
        items(data.size, key = {
            data[it].id
        }) {
            ListItem(name = data[it].name ?: "", modifier = Modifier.animateItem().fillMaxWidth())
        }
    }
}


@Preview(widthDp = 320, heightDp = 400, showBackground = true)
@Composable
fun ListHeaderPreview(modifier: Modifier = Modifier) {
    ListHeader(text = "List ID", 1, isExpanded = true) {

    }
}
