package com.ents_h108.petwell.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Doctor(
    @SerializedName("_id")val id: String,
    val image: Int,
    val namadokter: String,
    val tempatbekerja: String,
    val spesialis: String,
    val pengalaman: String,
    val harga: String,
    val lat: Double,
    val lon: Double
) : Parcelable