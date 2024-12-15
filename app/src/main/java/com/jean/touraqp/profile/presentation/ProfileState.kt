package com.jean.touraqp.profile.presentation

data class ProfileState(
    val isLoading: Boolean = false,
    val originalUsername : String = "",
    val username: String = "",
    val originalName : String = "",
    val name: String = "",
    val originalEmail : String= "",
    val email: String = "",
) {
    val isProfileModified: Boolean
        get() {
            return username != originalUsername ||
                    name != originalName ||
                    email != originalEmail
        }
}