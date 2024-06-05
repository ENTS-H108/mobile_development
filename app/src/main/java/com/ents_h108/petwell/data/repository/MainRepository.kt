package com.ents_h108.petwell.data.repository

import android.util.Log
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.remote.ApiConfig
import com.ents_h108.petwell.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.withContext

class MainRepository {

    private val apiService: ApiService = ApiConfig.getApiService()

    suspend fun getArticles(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getArticles()
                Log.d("MainRepository", "Response: $response")
                if (!response.error) {
                    Log.d("MainRepository", "Articles: ${response.listArticle}")
                    Result.Success(response.listArticle)
                } else {
                    Log.d("MainRepository", "Error Message: ${response.message}")
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Log.d("MainRepository", "Exception: ${e.message}")
                Result.Error(e.message ?: "get ")
            }
        }
    }
}