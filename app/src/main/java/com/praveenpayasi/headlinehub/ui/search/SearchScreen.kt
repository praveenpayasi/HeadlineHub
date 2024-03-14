package com.praveenpayasi.headlinehub.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.praveenpayasi.headlinehub.R
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.ui.base.ArticleList
import com.praveenpayasi.headlinehub.ui.base.ShowError
import com.praveenpayasi.headlinehub.ui.base.ShowLoading
import com.praveenpayasi.headlinehub.ui.base.UiState
import com.praveenpayasi.headlinehub.ui.theme.gray40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenRoute(
    onNewsClick: (url: String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val searchUiState: UiState<List<TopHeadlineEntity>> by searchViewModel.searchUiState.collectAsStateWithLifecycle()

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(
        modifier =
        Modifier
            .background(gray40)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(query = text, onQueryChange = {
            text = it
            searchViewModel.searchNews(it)
        }, onSearch = {
            active = false
        }, active = active, placeholder = {
            Text(text = stringResource(R.string.search_news))
        }, leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }, content = {
            SearchNewsList(searchUiState, onNewsClick, searchViewModel)
        }, onActiveChange = {
            active = it
        }, tonalElevation = 0.dp
        )
    }
}

@Composable
fun SearchNewsList(
    uiState: UiState<List<TopHeadlineEntity>>,
    onNewsClick: (url: String) -> Unit,
    searchViewModel: SearchViewModel
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
                searchViewModel.createNewsFlow()
            }
        }
    }
}