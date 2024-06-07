package com.ents_h108.petwell.data.model

data class NewPassword(
    val newPassword: String,
    val token: String
)

data class ResetPasswordResponse(
    val message: String
)
