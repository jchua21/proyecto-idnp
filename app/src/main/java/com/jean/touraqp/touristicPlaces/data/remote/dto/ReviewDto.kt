package com.jean.touraqp.touristicPlaces.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.jean.touraqp.core.utils.Identifiable

data class ReviewDto(
    override var id : String? = "",
    val userId: String = "",
    val touristicPlaceId: String= "",
    val comment: String = "",
    val rating: Double = 0.0,
): Identifiable

data class ReviewInsertDto(
    val userId: String = "",
    val touristicPlaceId: String= "",
    val comment: String = "",
    val rating: Double = 0.0,
)
