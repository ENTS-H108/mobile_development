package com.ents_h108.petwell.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.model.ArticleResponse
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.data.model.PetResponse
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class MainRepository(
    private val pref: UserPreferences,
    private val apiService: ApiService
) {

    fun getArticles(type: String): LiveData<Result<List<Article>>> = liveData {
        emit(Result.Loading)
        try {
            val token = pref.getToken().first()
            val response = apiService.getArticles(type, "Bearer $token")
            emit(Result.Success(response.listArticle))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ArticleResponse::class.java)
            emit(Result.Error(errorBody.error.toString()))
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
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PetResponse::class.java)
            emit(Result.Error(errorBody.error.toString()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}
