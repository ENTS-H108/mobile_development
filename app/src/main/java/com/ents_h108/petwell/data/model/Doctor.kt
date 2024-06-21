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
data class DoctorInvoice(
    val doctor: Doctor,
    val date: String,
    val workHour: WorkHours
)


@Keep
data class WorkSchedule(
    val scheduleId: String,
    val date: String,
    val workHours: List<WorkHours>
)

@Keep
@Parcelize
data class WorkHours(
    val workHourId: String,
    val availSlot: String,
    val isAvail: Boolean
) : Parcelable

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
