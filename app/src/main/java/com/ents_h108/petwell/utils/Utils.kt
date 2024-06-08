package com.ents_h108.petwell.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.ents_h108.petwell.R
import java.security.MessageDigest
import java.util.UUID

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

}
