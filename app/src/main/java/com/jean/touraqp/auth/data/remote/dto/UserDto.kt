package com.jean.touraqp.auth.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.jean.touraqp.auth.domain.authentication.model.UserDomain

data class UserDto(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val username: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
)

fun UserDto.toUserDomain(): UserDomain {
    return UserDomain(
        id = id,
        email = email,
        name = name,
        username = username,
    )
}