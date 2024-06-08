package com.ents_h108.petwell.data.remote

import com.ents_h108.petwell.data.model.ArticleResponse
import com.ents_h108.petwell.data.model.LoginRequest
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.NewPassword
import com.ents_h108.petwell.data.model.PetResponse
import com.ents_h108.petwell.data.model.ResetPasswordResponse
import com.ents_h108.petwell.data.model.SignUpRequest
import com.ents_h108.petwell.data.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    @POST("signup")
    suspend fun register(@Body request: SignUpRequest): SignUpResponse
    @POST("forgot-password")
    suspend fun reqToken(@Body body: Map<String, String>): ResetPasswordResponse
    @POST("reset-password")
    suspend fun resetPassword(@Body request: NewPassword): ResetPasswordResponse
    @GET("/articles")
    suspend fun getArticles(
        @Query("type") type: String,
        @Header("Authorization") token: String
    ): ArticleResponse
    @GET("/pets")
    suspend fun getPets(
        @Header("Authorization") token: String
    ): PetResponse
}
