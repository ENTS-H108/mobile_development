package com.ents_h108.petwell.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
data class DoctorResponse(
    val doctors: List<Doctor>,
    val error: Boolean,
    val message: String
)

@Keep
data class DoctorSchedule(
    val doctor: Doctor,
    val schedules: List<WorkSchedule>
)

@Keep
data class WorkSchedule(
    val date: String,
    val workHours: List<WorkHours>
)

@Keep
data class WorkHours(
    val availSlot: String,
    val isAvail: Boolean
)

@Keep
@Parcelize
data class Doctor(
    val id: String,
    val name: String,
    val type: String,
    val profpict: String?,
    val profile: String?,
    val experiences: String?,
    val year: String?,
    val lat: Double,
    val long: Double,
    val price: String,
    val hospital: String
) : Parcelable
