package com.jean.touraqp.touristicPlaces.data.repository.touristicPlace.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.core.utils.toObjectWithId
import com.jean.touraqp.touristicPlaces.data.mapper.toTouristicPlace
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.data.repository.touristicPlace.TouristicPlaceRepositoryImp.Companion.TAG
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TouristicPlaceRemoteDatasource @Inject constructor(
    private val db: FirebaseFirestore
) {
    private val touristicPlaceCollection = db.collection(DBCollection.TOURISTIC_PLACE)

    suspend fun getTouristicPlaces(query: String): List<TouristicPlace> {
        Log.d(TAG, "getTouristicPlaces: ${query}")
        val touristicPlacesReference = if (query.isNotEmpty()) {
            touristicPlaceCollection
                .whereEqualTo("name", query)
                .limit(3)
                .get()
                .await()
                .documents
        } else {
            touristicPlaceCollection
                .limit(3)
                .get()
                .await()
                .documents
        }

//        val touristicPlacesReference = touristicPlaceCollection.limit(3).get().await().documents
        val touristicPlacesDto = touristicPlacesReference.map { it.toObjectWithId<TouristicPlaceDto>() }
        val touristicPlaces = touristicPlacesDto.map { it.toTouristicPlace() }
        return touristicPlaces
    }
}