package com.ents_h108.petwell.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PetResponse(
    val pets: List<Pet>,
    val error: Boolean,
    val message: String
)

@Keep
data class Pet(
    @SerializedName("_id") val id: String,
    val name: String,
    val species: String,
    val age: Int,
    val owner: String,
    @SerializedName("__v") val v: Int,
)