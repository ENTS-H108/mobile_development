package com.ents_h108.petwell.data.model

data class UserResponse(
    val message: String,
    val user: User
)

data class User(
    val email: String,
    val username: String,
    val profilePict: String
)
