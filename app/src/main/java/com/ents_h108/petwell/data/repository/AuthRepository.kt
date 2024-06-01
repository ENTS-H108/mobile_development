package com.ents_h108.petwell.data.repository

import com.ents_h108.petwell.data.model.LoginRequest
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.remote.ApiConfig
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthRepository {

    private val apiService: ApiService = ApiConfig.getApiService()

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
