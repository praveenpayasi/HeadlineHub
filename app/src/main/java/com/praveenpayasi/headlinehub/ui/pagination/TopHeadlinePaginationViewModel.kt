package com.praveenpayasi.headlinehub.ui.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import com.praveenpayasi.headlinehub.data.repository.TopHeadlinePaginationRepository
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinePaginationViewModel @Inject constructor(
    private val paginationTopHeadlineRepository: TopHeadlinePaginationRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _topHeadlineUiState = MutableStateFlow<PagingData<ApiTopHeadlines>>(value = PagingData.empty())

    val topHeadlineUiState: StateFlow<PagingData<ApiTopHeadlines>> = _topHeadlineUiState

    init {
        startFetchingArticles()
    }

    private fun startFetchingArticles() {
        viewModelScope.launch(dispatcherProvider.main) {
            paginationTopHeadlineRepository.getTopHeadlinesArticles()
                .flowOn(dispatcherProvider.io)
                .catch { e -> }
                .collect {
                    _topHeadlineUiState.value = it
                }
        }
    }

}