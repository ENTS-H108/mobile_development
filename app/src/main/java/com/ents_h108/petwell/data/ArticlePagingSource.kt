package com.ents_h108.petwell.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.data.repository.UserPreferences
import kotlinx.coroutines.flow.first

class ArticlePagingSource(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
    private val type: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val token = userPreferences.getToken().first() ?: ""
            val responseData = apiService.getArticles(type, params.key ?: INITIAL_PAGE_INDEX, params.loadSize, "Bearer $token")
            return LoadResult.Page(
                data = responseData.listArticle,
                prevKey = if ((params.key ?: INITIAL_PAGE_INDEX) == INITIAL_PAGE_INDEX) null else (params.key ?: INITIAL_PAGE_INDEX) - 1,
                nextKey = if (responseData.listArticle.isEmpty()) null else (params.key ?: INITIAL_PAGE_INDEX) + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
