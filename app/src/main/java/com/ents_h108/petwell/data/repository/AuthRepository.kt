package com.ents_h108.petwell.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ents_h108.petwell.data.model.*
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

class AuthRepository(private val apiService: ApiService) {

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(mapOf("email" to email, "password" to password))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()?.let {
                try {
                    val loginResponse = Gson().fromJson(it, LoginResponse::class.java)
                    loginResponse.error ?: "Unknown error"
                } catch (jsonException: JsonSyntaxException) {
                    it
                }
            }
            emit(Result.Error(errorMessage ?: "Unknown error"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registerUser(name: String, email: String, password: String): LiveData<Result<MessageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(mapOf("username" to name, "email" to email, "password" to password))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun requestToken(email: String): LiveData<Result<MessageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.reqToken(mapOf("email" to email))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun resetPassword(newPassword: String, token: String): LiveData<Result<MessageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.resetPassword(mapOf("newPassword" to newPassword, "token" to token))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun googleAuth(token: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.googleAuth(mapOf("token" to token))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            emit(Result.Error(errorBody.error ?: "Unknown error"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}
