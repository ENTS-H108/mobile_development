package com.ents_h108.petwell.data.remote

import com.ents_h108.petwell.data.model.ArticleResponse
import com.ents_h108.petwell.data.model.LoginRequest
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.NewPassword
import com.ents_h108.petwell.data.model.RequestToken
import com.ents_h108.petwell.data.model.ResetPasswordResponse
import com.ents_h108.petwell.data.model.SignUpRequest
import com.ents_h108.petwell.data.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    @POST("signup")
    suspend fun register(@Body request: SignUpRequest): SignUpResponse
    @POST("forgot-password")
    suspend fun reqToken(@Body request: RequestToken): ResetPasswordResponse
    @POST("reset-password")
    suspend fun resetPassword(@Body request: NewPassword): ResetPasswordResponse


    @GET("article")
    suspend fun getArticles(): ArticleResponse


//    @GET("forget-password")
//    fun getTokenResetPassword(
//        @Query("email") email: String
//    ): Call<List<SignUpResponse>>
//
//    @POST("reset-password")
//    fun resetPassword(
//        @Query("token") token: String,
//        @Body raw: JSONObject
//    ): Call<List<SignUpResponse>>
}
