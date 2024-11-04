package com.jean.touraqp.auth.ui.model

import com.jean.touraqp.auth.domain.authentication.model.UserDomain

data class UserUI(
    val id: String? = null,
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "" ,
    val confirmPassword: String = ""
)

fun UserUI.toUserDomain(): UserDomain {
    return UserDomain(
        email = email,
        name = name,
        password = password,
        username = username,
    )
}