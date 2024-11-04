package com.jean.touraqp.core

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var id: String? = null
        private set
    var name: String? = null
        private set
    var email: String? = null
        private set
    var username: String? = null
        private set

    fun updateSession(id: String, name: String, email: String, username: String) {
        this.id = id
        this.name = name
        this.email = email
        this.username = username
    }

    fun clearSession() {
         id = null
         name = null
         email = null
         username = null
    }
}