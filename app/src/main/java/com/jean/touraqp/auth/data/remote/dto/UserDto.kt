package com.jean.touraqp.auth.data.remote.dto

import com.google.firebase.Timestamp
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Identifiable

data class UserDto(
    override var id: String? = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val username: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
): Identifiable

fun UserDto.toUser(): User {
    return User(
        id = id,
        email = email,
        name = name,
        username = username,
    )
}