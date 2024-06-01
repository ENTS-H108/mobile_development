package com.ents_h108.petwell.data.remote

import com.ents_h108.petwell.data.model.LoginRequest
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.SignUpResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
//
//    @GET("register")
//    fun signUp(
//        @Query("username") username: String,
//        @Query("email") email: String,
//        @Query("email") password: String,
//    ): Call<List<SignUpResponse>>
//
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
