package com.praveenpayasi.headlinehub.viewmodel

import app.cash.turbine.test
import com.praveenpayasi.headlinehub.data.local.entity.Source
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiSource
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import com.praveenpayasi.headlinehub.data.model.topheadlines.toTopHeadlineEntity
import com.praveenpayasi.headlinehub.data.repository.OfflineTopHeadlineRepository
import com.praveenpayasi.headlinehub.ui.base.UiState
import com.praveenpayasi.headlinehub.ui.offline.OfflineTopHeadlineViewModel
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import com.praveenpayasi.headlinehub.ui.utils.NetworkHelperImpl
import com.praveenpayasi.headlinehub.ui.utils.logger.Logger
import com.praveenpayasi.headlinehub.utils.TestDispatcherProvider
import com.praveenpayasi.headlinehub.utils.TestLogger
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OfflineTopHeadlineViewModelTest {

    @Mock
    private lateinit var offlineTopHeadlinesRepository: OfflineTopHeadlineRepository

    @Mock
    private lateinit var networkConnection: NetworkHelperImpl

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var logger: Logger

    private lateinit var offlineTopHeadlineViewModel: OfflineTopHeadlineViewModel

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        logger = TestLogger()
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            val apiSource = ApiSource(
                id = "sourceId", name = "sourceName"
            )
            val apiArticle = ApiTopHeadlines(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val listOfArticleAPI = mutableListOf<ApiTopHeadlines>()
            listOfArticleAPI.add(apiArticle)

            val article = apiArticle.toTopHeadlineEntity(country)
            val listOfArticle = mutableListOf<TopHeadlineEntity>()
            listOfArticle.add(article)

            doReturn(true).`when`(networkConnection).isNetworkConnected()

            doReturn(flowOf(listOfArticleAPI)).`when`(offlineTopHeadlinesRepository)
                .getTopHeadlines(country)
            doNothing().`when`(offlineTopHeadlinesRepository)
                .deleteAndInsertAllTopHeadlinesArticles(listOfArticle, country)

            offlineTopHeadlineViewModel = OfflineTopHeadlineViewModel(offlineTopHeadlinesRepository, dispatcherProvider, networkConnection, logger)
            verify(offlineTopHeadlinesRepository, times(1)).getTopHeadlines(country)
            verify(offlineTopHeadlinesRepository, times(1)).deleteAndInsertAllTopHeadlinesArticles(listOfArticle, country)
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            doReturn(true).`when`(networkConnection).isNetworkConnected()
            val country = AppConstant.COUNTRY
            val errorMessage = "Error Message For You"
            doReturn(flow<List<TopHeadlineEntity>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(offlineTopHeadlinesRepository).getTopHeadlines(country)
            offlineTopHeadlineViewModel = OfflineTopHeadlineViewModel(offlineTopHeadlinesRepository, dispatcherProvider, networkConnection, logger)
            offlineTopHeadlineViewModel.topHeadlineUiState.test {
                assertEquals(UiState.Error(IllegalStateException(errorMessage).toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(offlineTopHeadlinesRepository, times(1)).getTopHeadlines(country)
        }
    }

    @Test
    fun fetchNewsNoInternet_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            val source = Source(
                id = "sourceId", name = "sourceName"
            )
            val article = TopHeadlineEntity(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )

            val listOfArticle = mutableListOf<TopHeadlineEntity>()
            listOfArticle.add(article)

            doReturn(false).`when`(networkConnection).isNetworkConnected()

            doReturn(flowOf(listOfArticle)).`when`(offlineTopHeadlinesRepository)
                .getTopHeadlinesFromDB(country)

            offlineTopHeadlineViewModel = OfflineTopHeadlineViewModel(offlineTopHeadlinesRepository, dispatcherProvider, networkConnection, logger)

            offlineTopHeadlineViewModel.topHeadlineUiState.test {
                assertEquals(UiState.Success(listOfArticle), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(offlineTopHeadlinesRepository, times(1)).getTopHeadlinesFromDB(country)
        }
    }

    @Test
    fun fetchNewsNoInternet_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            doReturn(false).`when`(networkConnection).isNetworkConnected()
            val errorMessage = "Error Message For You"
            doReturn(flow<List<TopHeadlineEntity>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(offlineTopHeadlinesRepository).getTopHeadlinesFromDB(country)
            offlineTopHeadlineViewModel = OfflineTopHeadlineViewModel(offlineTopHeadlinesRepository, dispatcherProvider, networkConnection, logger)
            offlineTopHeadlineViewModel.topHeadlineUiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(offlineTopHeadlinesRepository, times(1)).getTopHeadlinesFromDB(country)
        }
    }
}