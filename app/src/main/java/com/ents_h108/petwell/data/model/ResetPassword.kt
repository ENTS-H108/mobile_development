package com.ents_h108.petwell.data.model

data class RequestToken(
    val email: String
)

data class NewPassword(
    val token: String,
    val newPassword: String
)

data class ResetPasswordResponse(
    val message: String
)
