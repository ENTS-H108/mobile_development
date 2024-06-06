package com.ents_h108.petwell.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

class AuthRepository {

    private val apiService: ApiService = ApiConfig.getApiService()

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            emit(Result.Error(errorBody.error))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registerUser(name: String, email: String, password: String): LiveData<Result<SignUpResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(SignUpRequest(email, name, password))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, SignUpResponse::class.java)
            emit(Result.Error(errorBody.error))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
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
