package com.ents_h108.petwell.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String
)

data class Login(
    val token: String,
    val provider: String,
    val email: String,
    val username: String,
    val profilePict: String
)
