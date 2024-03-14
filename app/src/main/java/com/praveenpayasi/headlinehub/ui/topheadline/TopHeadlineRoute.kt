package com.praveenpayasi.headlinehub.ui.topheadline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.ui.base.ArticleList
import com.praveenpayasi.headlinehub.ui.base.ShowError
import com.praveenpayasi.headlinehub.ui.base.ShowLoading
import com.praveenpayasi.headlinehub.ui.base.UiState

@Composable
fun TopHeadlineRoute(
    onNewsClick: (url: String) -> Unit,
    topHeadlineViewModel: TopHeadlineViewModel = hiltViewModel()
) {

    val topHeadlineUiState: UiState<List<TopHeadlineEntity>> by topHeadlineViewModel.topHeadlineUiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(4.dp)) {
        TopHeadlineScreen(topHeadlineUiState, onNewsClick, onRetryClick = {
            topHeadlineViewModel.startFetchingTopHeadlines()
        })
    }
}

@Composable
fun TopHeadlineScreen(
    uiState: UiState<List<TopHeadlineEntity>>,
    onNewsClick: (url: String) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            ArticleList(uiState.data, onNewsClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(text = uiState.message) {
                onRetryClick()
            }
        }
    }
}