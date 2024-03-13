package com.praveenpayasi.headlinehub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import com.praveenpayasi.headlinehub.ui.utils.AppConstant.PAGE_SIZE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TopHeadlinePaginationRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlinesArticles(): Flow<PagingData<ApiTopHeadlines>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { TopHeadlinePagingSource(networkService)
            }).flow
    }
}