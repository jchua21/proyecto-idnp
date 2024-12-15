package com.jean.touraqp.auth.domain.authentication.model

import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.core.utils.Identifiable

data class User(
    override  var id : String? = null ,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val username: String = "",
    val imageUrl: String = "https://utfs.io/f/AFyLEdVPOp24dt2Kt3A0gin2dHAV89k4PWEMefKrjO1mR3Zu"
): Identifiable

fun User.toUserUI(): UserUI {
    return UserUI(
        id = id,
        email= email,
        name = name,
        imageUrl = imageUrl,
        username = username,
    )
}

fun User.toUserDTO(): UserDto{
    return UserDto(
        email = email,
        name = name,
        password = password,
        username = username,

    )
}

