package com.ents_h108.petwell.utils

import android.content.Context
import android.location.Geocoder
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.ents_h108.petwell.R
import java.security.MessageDigest
import java.util.Locale
import java.util.UUID
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object Utils {

    fun generateHashedNonce(): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(UUID.randomUUID().toString().toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }

    fun showError(editText: AppCompatEditText, context: Context) {
        editText.background = ContextCompat.getDrawable(context, R.drawable.rounded_et_error)
    }

    fun resetError(editText: AppCompatEditText, context: Context) {
        editText.background = ContextCompat.getDrawable(context, R.drawable.rounded_et)
    }
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = sin(Math.toRadians(lat1)) * sin(Math.toRadians(lat2)) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * cos(Math.toRadians(theta))
        dist = acos(dist)
        dist = Math.toDegrees(dist)
        dist *= 60.0 * 1.1515
        return dist * 1.609344
    }

    fun getAddressFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double,
        callback: (String?, String?, String?) -> Unit // Modify the callback to return street name and number
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val city = address.subAdminArea
            val street = address.thoroughfare
            val number = address.subThoroughfare
            callback(city, street, number)
        } else {
            callback(null, null, null)
        }
    }
}
