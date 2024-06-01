package com.ents_h108.petwell.data.repository

import com.ents_h108.petwell.data.model.LoginRequest
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.NewPassword
import com.ents_h108.petwell.data.model.RequestToken
import com.ents_h108.petwell.data.model.ResetPasswordResponse
import com.ents_h108.petwell.data.model.SignUpRequest
import com.ents_h108.petwell.data.model.SignUpResponse
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

    suspend fun register(email: String, username: String, password: String): Result<SignUpResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val signUpRequest = SignUpRequest(email, username, password)
                val response = apiService.register(signUpRequest)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    suspend fun requestToken(email: String): Result<ResetPasswordResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val requestToken = RequestToken(email)
                val response = apiService.reqToken(requestToken)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    suspend fun resetPassword(token: String, newPassword: String): Result<ResetPasswordResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val resetPassword = NewPassword(token, newPassword)
                val response = apiService.resetPassword(resetPassword)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
