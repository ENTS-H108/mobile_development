package com.ents_h108.petwell.data.model

data class SignUpRequest(
    val email: String,
    val username: String,
    val password: String
)

data class SignUpResponse(
    val message: String,
    val error: String
)
