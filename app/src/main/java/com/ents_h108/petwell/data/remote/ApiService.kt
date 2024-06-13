package com.ents_h108.petwell.data.remote

import com.ents_h108.petwell.data.model.ArticleResponse
import com.ents_h108.petwell.data.model.LoginRequest
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.NewPassword
import com.ents_h108.petwell.data.model.PetResponse
import com.ents_h108.petwell.data.model.ResetPasswordResponse
import com.ents_h108.petwell.data.model.SignUpRequest
import com.ents_h108.petwell.data.model.SignUpResponse
import com.ents_h108.petwell.data.model.User
import com.ents_h108.petwell.data.model.UserResponse
import com.ents_h108.petwell.data.model.EditPet
import com.ents_h108.petwell.data.model.History
import com.ents_h108.petwell.data.model.Pet
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): ArticleResponse
    @GET("/pets")
    suspend fun getPets(
        @Header("Authorization") token: String
    ): PetResponse
    @PUT("pets/{id}")
    suspend fun editPet(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body editpet: EditPet
    ): PetResponse
    @GET("pets/{id}")
    suspend fun getPet(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Pet
    @POST("pets")
    suspend fun addPet(
        @Header("Authorization") token: String,
        @Body editpet: EditPet
    ): PetResponse
    @DELETE("pets/{id}")
    suspend fun deletePet(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): PetResponse
    @PUT("profile")
    suspend fun updateProfileUser(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): UserResponse
    @GET("profile")
    suspend fun getProfileUser(
        @Header("Authorization") token: String
    ): User
    @POST("pets/{id}/addhistory")
    suspend fun addHistory(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body history: History
    ): History
}
