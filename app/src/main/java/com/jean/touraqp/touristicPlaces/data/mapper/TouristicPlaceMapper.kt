package com.jean.touraqp.touristicPlaces.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.jean.touraqp.touristicPlaces.data.local.entities.TouristicPlaceEntity
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

fun TouristicPlaceDto.toTouristicPlace(id: String) : TouristicPlace {
    return TouristicPlace(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        longitude = longitude,
        latitude = latitude,
        reviews = reviews.map { it.toReview() }
    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        username = username,
        rate = rate,
        comment = comment
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