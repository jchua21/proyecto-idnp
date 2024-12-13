package com.jean.touraqp.touristicPlaces.data.mapper

import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.data.remote.dto.toUser
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.domain.model.Review


suspend fun ReviewDto.toReview(user: UserDto): Review {

    return Review(
        user = user.toUser(), //todo change
        rate = rate,
        comment = comment,
    )
}
