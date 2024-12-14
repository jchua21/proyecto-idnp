package com.jean.touraqp.touristicPlaces.presentation.mapper

import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlaceWithReviews
import com.jean.touraqp.touristicPlaces.presentation.model.TouristicPlaceWithReviewsUI


fun TouristicPlaceWithReviews.toPresentation(): TouristicPlaceWithReviewsUI {
    return TouristicPlaceWithReviewsUI(
        id = id,
        name = name,
        description = description,
        longitude = longitude,
        latitude = latitude,
        imageUrl = imageUrl,
        reviews = reviews.toMutableList()
    )
}

fun List<TouristicPlaceWithReviews>.toPresentation(): List<TouristicPlaceWithReviewsUI> {
    return this.map { it.toPresentation() }
}