package com.praveenpayasi.headlinehub.ui.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveenpayasi.headlinehub.data.local.entity.NewsSources
import com.praveenpayasi.headlinehub.data.repository.NewsSourceRepository
import com.praveenpayasi.headlinehub.ui.base.UiState
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import com.praveenpayasi.headlinehub.ui.utils.NetworkHelper
import com.praveenpayasi.headlinehub.ui.utils.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourcesViewModel @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _newsSourceUiState = MutableStateFlow<UiState<List<NewsSources>>>(UiState.Loading)

    val newsSourceUiState: StateFlow<UiState<List<NewsSources>>> = _newsSourceUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        startFetchingSource()
    }

    fun startFetchingSource() {
        if (networkHelper.isNetworkConnected()) {
            fetchNewsSource()
        } else {
            fetchNewsSourceFromDB()
        }
    }

    private fun fetchNewsSource() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getNewsSources().flowOn(dispatcherProvider.io).catch { e ->
                _newsSourceUiState.value = UiState.Error(e.toString())
            }.collect {
                _newsSourceUiState.value = UiState.Success(it)
            }
        }
    }

    private fun fetchNewsSourceFromDB() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getNewsSourcesFromDB()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsSourceUiState.value = UiState.Error(e.toString())
                }.collect {
                    if (!checkInternetConnection() && it.isEmpty()) {
                        _newsSourceUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _newsSourceUiState.value = UiState.Success(it)
                        logger.d("NewsSourcesViewModel", "Success")
                    }
                }
        }
    }
}