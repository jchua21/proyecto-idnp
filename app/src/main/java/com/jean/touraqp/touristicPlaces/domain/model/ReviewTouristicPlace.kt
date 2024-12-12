package com.jean.touraqp.touristicPlaces.domain.model

import com.google.firebase.Timestamp
import com.jean.touraqp.auth.domain.authentication.model.UserDomain
import com.jean.touraqp.auth.ui.model.UserUI

data class Review(
    val user: UserDomain,
    val comment: String,
    val rate: Double,
)