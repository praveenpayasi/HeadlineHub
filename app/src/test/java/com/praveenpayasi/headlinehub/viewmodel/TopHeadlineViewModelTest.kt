package com.praveenpayasi.headlinehub.viewmodel

import app.cash.turbine.test
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.data.repository.TopHeadlineRepository
import com.praveenpayasi.headlinehub.ui.base.UiState
import com.praveenpayasi.headlinehub.ui.topheadline.TopHeadlineViewModel
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import com.praveenpayasi.headlinehub.ui.utils.NetworkHelper
import com.praveenpayasi.headlinehub.ui.utils.logger.Logger
import com.praveenpayasi.headlinehub.utils.TestDispatcherProvider
import com.praveenpayasi.headlinehub.utils.TestLogger
import com.praveenpayasi.headlinehub.utils.TestNetworkHelper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    @Mock
    private lateinit var topHeadlinesRepository: TopHeadlineRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var networkHelper: NetworkHelper

    private lateinit var logger: Logger

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        logger = TestLogger()
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            doReturn(flowOf(emptyList<TopHeadlineEntity>()))
                .`when`(topHeadlinesRepository)
                .getTopHeadlines(country)
            val viewModel = TopHeadlineViewModel(
                topHeadlinesRepository,
                dispatcherProvider,
                networkHelper,
                logger
            )
            viewModel.topHeadlineUiState.test {
                assertEquals(UiState.Success(emptyList<List<TopHeadlineEntity>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(topHeadlinesRepository, times(1)).getTopHeadlines(country)
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            val errorMessage = "Error Message For You"
            doReturn(flow<List<TopHeadlineEntity>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(topHeadlinesRepository)
                .getTopHeadlines(country)

            val viewModel = TopHeadlineViewModel(
                topHeadlinesRepository,
                dispatcherProvider,
                networkHelper,
                logger
            )
            viewModel.topHeadlineUiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(topHeadlinesRepository, times(1)).getTopHeadlines(country)
        }
    }

}