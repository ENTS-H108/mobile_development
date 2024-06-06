package com.ents_h108.petwell.data.repository

import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.remote.ApiConfig
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {

    private val apiService: ApiService = ApiConfig.getApiService()

    suspend fun getArticles(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getArticles()
                if (!response.error) {
                    Result.Success(response.listArticle)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
