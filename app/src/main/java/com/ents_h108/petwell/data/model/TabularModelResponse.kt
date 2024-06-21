package com.ents_h108.petwell.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TabularModelResponse (
    val status: String,
    val message: String,
    val data: PredictionResult
)

@Keep
data class PredictionResult (
    val id: String,
    val result: String,
    val suggestion: String,
    val explanation: String,
    @SerializedName("created_at") val timestamp: String
)