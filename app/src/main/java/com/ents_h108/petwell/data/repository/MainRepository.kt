package com.ents_h108.petwell.data.repository

import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.remote.ApiConfig
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainRepository (
    private val pref: UserPreferences
) {

    private val apiService: ApiService = ApiConfig.getApiService()

    suspend fun getArticles(type: String): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                val token = runBlocking {
                    pref.getToken().first()
                }
                val response = apiService.getArticles(type, "Bearer $token")
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
