package com.ents_h108.petwell.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ents_h108.petwell.data.ArticlePagingSource
import com.ents_h108.petwell.data.model.*
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class MainRepository(
    private val pref: UserPreferences,
    private val apiService: ApiService
) {
    private suspend fun getToken() = pref.getToken().first()

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            errorBody?.let {
                val errorResponse = Gson().fromJson(it, MessageResponse::class.java)
                errorResponse.message
            } ?: "Unknown error occurred"
        } catch (e: JsonSyntaxException) {
            "Failed to parse error message"
        } catch (e: IOException) {
            "Failed to read error message"
        }
    }

    fun getArticles(type: String): LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 3, initialLoadSize = 3),
            pagingSourceFactory = { ArticlePagingSource(apiService, pref, type) }
        ).liveData
    }

    fun getPets(): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.getPets("Bearer $token")
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editPets(id: String, name: String, species: String, age: Int): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.editPet(id, "Bearer $token", EditPet(name, species, age))
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getPet(id: String): LiveData<Result<Pet>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.getPet(id, "Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addPets(name: String, species: String, age: Int): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.addPet("Bearer $token", EditPet(name, species, age))
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deletePet(id: String): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.deletePet(id, "Bearer $token")
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editProfile(username: String, profilePict: String?): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val body = mutableMapOf<String, String>().apply {
                put("username", username)
                profilePict?.let { put("profilePict", it) }
            }
            val response = apiService.updateProfileUser("Bearer $token", body)
            emit(Result.Success(response.user))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getProfileUser(): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.getProfileUser("Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addHistory(id: String, type: Int): LiveData<Result<History>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.addHistory("Bearer $token", id, mapOf("type" to type))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun changePassword(currPw: String, newPw: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.changePassword("Bearer $token", mapOf("currPassword" to currPw, "newPassword" to newPw))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDoctorList(): LiveData<Result<Doctor>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.getAllDoctor("Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getTabularResponse(request: List<Any>): LiveData<Result<TabularModelResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = getToken()
            val response = apiService.getTabularResponse("Bearer $token", request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(parseErrorMessage(e.response()?.errorBody()?.string())))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}
