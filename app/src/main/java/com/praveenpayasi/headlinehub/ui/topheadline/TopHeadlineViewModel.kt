package com.praveenpayasi.headlinehub.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveenpayasi.headlinehub.data.model.topheadlines.Article
import com.praveenpayasi.headlinehub.data.repository.TopHeadlineRepository
import com.praveenpayasi.headlinehub.ui.base.UiState
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import com.praveenpayasi.headlinehub.ui.utils.NetworkHelper
import com.praveenpayasi.headlinehub.ui.utils.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val logger: Logger
) : ViewModel() {

    private val _topHeadlineUiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val topHeadlineUiState: StateFlow<UiState<List<Article>>> = _topHeadlineUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        startFetchingArticles()
    }

    fun startFetchingArticles() {
        if (checkInternetConnection()) {
            fetchArticles()
        } else {
            _topHeadlineUiState.value = UiState.Error("Data Not found.")
        }
    }

    private fun fetchArticles() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlinesArticles(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                }.collect {
                    _topHeadlineUiState.value = UiState.Success(it)
                    logger.d("TopHeadlineViewModel", "Success")
                }
        }
    }
}