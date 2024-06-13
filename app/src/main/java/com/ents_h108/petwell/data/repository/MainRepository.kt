package com.ents_h108.petwell.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ents_h108.petwell.data.ArticlePagingSource
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.model.ArticleResponse
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.data.model.PetResponse
import com.ents_h108.petwell.data.model.User
import com.ents_h108.petwell.data.model.UserResponse
import com.ents_h108.petwell.data.model.EditPet
import com.ents_h108.petwell.data.model.History
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class MainRepository(
    private val pref: UserPreferences,
    private val apiService: ApiService
) {
    fun getArticles(type: String, coroutine: CoroutineScope): LiveData<Result<PagingData<Article>>> = liveData {
        emit(Result.Loading)
        try {
            val pager = Pager(
                config = PagingConfig(pageSize = 3, initialLoadSize = 3),
                pagingSourceFactory = {ArticlePagingSource(apiService, pref, type)}
            )
            pager.flow.cachedIn(coroutine).collect{
                emit(Result.Success(it))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ArticleResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getPets(): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.getPets( "Bearer $token")
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            try {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, PetResponse::class.java)
                if (errorBody != null && errorBody.error) {
                    emit(Result.Error(errorBody.message))
                } else {
                    emit(Result.Error("Unknown error occurred"))
                }
            } catch (jsonException: JsonSyntaxException) {
                emit(Result.Error("Failed to parse error message"))
            } catch (ioException: IOException) {
                emit(Result.Error("Failed to read error message"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editPets(id: String, name: String, species: String): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.editPet(id, "Bearer $token", EditPet(name, species))
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PetResponse::class.java)
            emit(Result.Error(errorBody.error.toString()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getPet(id: String): LiveData<Result<Pet>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.getPet(id, "Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            emit(Result.Error(errorBody ?: "Unknown error occurred"))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    fun addPets(name: String, species: String): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.addPet("Bearer $token", EditPet(name, species))
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PetResponse::class.java)
            emit(Result.Error(errorBody.error.toString()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deletePet(id: String): LiveData<Result<List<Pet>>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.deletePet(id, "Bearer $token")
            emit(Result.Success(response.pets))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PetResponse::class.java)
            emit(Result.Error(errorBody.error.toString()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editProfile(username: String, profilepict: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.updateProfileUser("Bearer $token", mapOf("username" to username, "profilePict" to profilepict))
            emit(Result.Success(response.user))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, UserResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getProfileUser(): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.getProfileUser("Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            emit(Result.Error(errorBody ?: "Unknown error occurred"))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    fun addHistory(id: String, type: Int, timestamp: String): LiveData<Result<History>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.addHistory("Bearer $token", id, History(type, timestamp))
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            emit(Result.Error(errorBody ?: "Unknown error occurred"))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }
}
