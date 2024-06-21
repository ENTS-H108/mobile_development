package com.ents_h108.petwell.data.remote

import com.ents_h108.petwell.data.model.ArticleResponse
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.data.model.DoctorSchedule
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.PetResponse
import com.ents_h108.petwell.data.model.User
import com.ents_h108.petwell.data.model.UserResponse
import com.ents_h108.petwell.data.model.EditPet
import com.ents_h108.petwell.data.model.History
import com.ents_h108.petwell.data.model.MessageResponse
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.data.model.TabularModelResponse
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
    suspend fun login(@Body request: Map<String, String>): LoginResponse

    @POST("signup")
    suspend fun register(@Body request: Map<String, String>): MessageResponse

    @POST("forgot-password")
    suspend fun reqToken(@Body body: Map<String, String>): MessageResponse

    @POST("reset-password")
    suspend fun resetPassword(@Body request: Map<String, String>): MessageResponse

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
        @Body history: Map<String, Int>
    ): History

    @POST("auth/google")
    suspend fun googleAuth(
        @Body body: Map<String, String>
    ): LoginResponse

    @PUT("profile/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): LoginResponse
    @GET("appointments")
    suspend fun getAllDoctor(
        @Header("Authorization") token: String
    ): List<Doctor>
    @GET("appointments/detail/{doctorId}")
    suspend fun getDetailDoctor(
        @Header("Authorization") token: String,
        @Path("id") doctorId: String
    ): Doctor
    @GET("appointments/detail")
    suspend fun getScheduleDoctor(
        @Header("Authorization") token: String,
        @Query("doctorId") doctorId: String,
    ): DoctorSchedule
    @GET("appointments/summary")
    suspend fun getInvoiceAppointment(
        @Header("Authorization") token: String,
        @Query("hour") hour: String?
    ): DoctorSchedule
    @GET("null")
    suspend fun getTabularResponse(
        @Header("Authorization") token: String,
        @Body request: List<Any>
    ): TabularModelResponse
}
