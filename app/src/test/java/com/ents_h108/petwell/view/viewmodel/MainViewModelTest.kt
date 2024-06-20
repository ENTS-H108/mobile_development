package com.ents_h108.petwell.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.ents_h108.petwell.testTools.ArticlePagingSource
import com.ents_h108.petwell.testTools.DataDummy
import com.ents_h108.petwell.testTools.MainDispatcherRule
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.model.User
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.testTools.getOrAwaitValue
import com.ents_h108.petwell.view.adapter.ArticleAdapter
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()
    @Mock
    private lateinit var mainRepository: MainRepository
    private lateinit var mainViewModel: MainViewModel
    @Mock
    private lateinit var preferences: UserPreferences

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(mainRepository, preferences)
    }

    @Test
    fun `when Get Content Should Not Null and Return Data`() = runTest {
        val dummyContent = DataDummy.generateDummyDataArticle()
        val data: PagingData<Article> = ArticlePagingSource.snapshot(dummyContent)
        val expectedQuote = MutableLiveData<PagingData<Article>>()
        expectedQuote.value = data
        `when`(mainRepository.getArticles("artikel")).thenReturn(expectedQuote)

        val actualQuote: PagingData<Article> = mainViewModel.getContent("artikel").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ArticleAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        assertNotNull(differ.snapshot())
        assertEquals(dummyContent.size, differ.snapshot().size)
        assertEquals(dummyContent[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Content Empty Should Return No Data`() = runTest {
        val data: PagingData<Article> = PagingData.from(emptyList())
        val expectedQuote = MutableLiveData<PagingData<Article>>()
        expectedQuote.value = data
        `when`(mainRepository.getArticles("artikel")).thenReturn(expectedQuote)

        val actualQuote: PagingData<Article> = mainViewModel.getContent("artikel").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ArticleAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        assertEquals(0, differ.snapshot().size)
    }

    @Test
    fun `when Fetch UserProfile Should Not Null and Return Success`() {
        val dummyUser = DataDummy.generateDummyDataUser()
        val expectedUser = MutableLiveData<Result<User>>()
        expectedUser.value = Result.Success(dummyUser)
        `when`(mainRepository.getProfileUser()).thenReturn(expectedUser)
        val actualUser = mainViewModel.fetchUserProfile().getOrAwaitValue()
        Mockito.verify(mainRepository).getProfileUser()
        assertNotNull(actualUser)
        assertTrue(actualUser is Result.Success)
        assertEquals(dummyUser, (actualUser as Result.Success).data)
    }

    @Test
    fun `when Get UserProfile Error Should Return Error`() {
        val user = MutableLiveData<Result<User>>()
        user.value = Result.Error("Error")
        `when`(mainRepository.getProfileUser()).thenReturn(user)
        val actualUser = mainViewModel.fetchUserProfile().getOrAwaitValue()
        Mockito.verify(mainRepository).getProfileUser()
        assertNotNull(actualUser)
        assertTrue(actualUser is Result.Error)
    }

    @Test
    fun `when Get Pet Should Not Null and Return Success`() {
        // Create Unit Test
    }

    @Test
    fun `when Get Pet Error Should Return Error`() {
        // Create Unit Test
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}