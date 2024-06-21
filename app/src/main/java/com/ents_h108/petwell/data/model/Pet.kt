package com.ents_h108.petwell.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class PetResponse(
    val pets: List<Pet>,
    val error: Boolean,
    val message: String
)

@Keep
@Parcelize
data class Pet(
    @SerializedName("_id") val id: String,
    val name: String,
    val species: String,
    val age: Int,
    val owner: String,
    val thumbnail: String?,
    val history: List<History>?
) : Parcelable

@Keep
@Parcelize
data class History(
    val type: Int,
    val timestamp: String
) : Parcelable

@Keep
data class EditPet(
    val name: String,
    val species: String,
    val age: Int
)
