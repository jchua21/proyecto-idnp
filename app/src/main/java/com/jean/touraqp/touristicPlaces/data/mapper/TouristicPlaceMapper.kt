package com.jean.touraqp.touristicPlaces.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.jean.touraqp.auth.ui.model.UserUI
import com.jean.touraqp.touristicPlaces.data.local.entities.TouristicPlaceEntity
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

fun TouristicPlaceDto.toTouristicPlace(id: String, reviews: List<Review>,) : TouristicPlace {
    return TouristicPlace(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        longitude = longitude,
        latitude = latitude,
        reviews = reviews
    )
}


fun TouristicPlaceDto.toTouristicPlaceEntity(id: String): TouristicPlaceEntity {
    return  TouristicPlaceEntity(
        id = id,
        name = name,
        description = description,
        longitude = longitude,
        latitude = latitude,
        imageUrl = imageUrl,
        createdAt = createdAt.toDate().time,
        updatedAt = updatedAt.toDate().time,
    )
}


fun TouristicPlaceEntity.toTouristicPlace(): TouristicPlace {
    return  TouristicPlace(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        longitude = longitude,
        latitude = latitude
    )
}