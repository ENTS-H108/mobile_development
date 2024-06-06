package com.ents_h108.petwell.data.model

import java.lang.Error

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String,
    val username: String,
    val error: String
)
