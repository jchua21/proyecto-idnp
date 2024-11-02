package com.jean.touraqp.auth.data.remote.dto

data class UserDto(
    val email: String,
    val name: String,
    val password: String,
    val username: String
)