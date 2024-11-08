package com.jean.touraqp.core.seed

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class TouristicPlacesSeed {

    suspend fun addTouristicPlaces() {
        val db = Firebase.firestore
        val sampleTouristicPlaces = listOf(
            TouristicPlaceDto(
                name = "Historic Cathedral of Arequipa",
                description = "A beautiful baroque-style cathedral located in the heart of the city.",
                longitude = "-71.536960",
                latitude = "-16.398866",
                address = "Plaza de Armas, Arequipa",
            ),
            TouristicPlaceDto(
                name = "Santa Catalina Monastery",
                description = "A large convent complex with vibrant architecture and rich history.",
                longitude = "-71.537000",
                latitude = "-16.395956",
                address = "Santa Catalina 301, Arequipa",
            ),
            TouristicPlaceDto(
                name = "Yanahuara Viewpoint",
                description = "A scenic viewpoint with sweeping views of the city and the Misti volcano.",
                longitude = "-71.542749",
                latitude = "-16.394805",
                address = "Callejón de la Iglesia, Yanahuara",

                ),
            TouristicPlaceDto(
                name = "Yanahuara Viewpoint",
                description = "A scenic viewpoint offering panoramic views of the city and the Misti volcano.",
                longitude = "-71.542500",
                latitude = "-16.394600",
                address = "Yanahuara, Arequipa, Peru"
            ),
            TouristicPlaceDto(
                name = "Misti Volcano",
                description = "A towering active stratovolcano that is iconic to Arequipa and popular for hiking.",
                longitude = "-71.406000",
                latitude = "-16.295000",
                address = "Arequipa, Peru"
            ),
            TouristicPlaceDto(
                name = "San Camilo Market",
                description = "A traditional market filled with local produce, crafts, and unique foods.",
                longitude = "-71.535500",
                latitude = "-16.400800",
                address = "San Camilo, Arequipa, Peru"
            ),
            TouristicPlaceDto(
                name = "Molino de Sabandía",
                description = "A historic water mill surrounded by beautiful landscapes, showcasing colonial engineering.",
                longitude = "-71.497000",
                latitude = "-16.440000",
                address = "Sabandía, Arequipa, Peru"
            ),
            TouristicPlaceDto(
                name = "La Compañía Church",
                description = "A baroque-style church known for its intricate carvings and historic significance.",
                longitude = "-71.537200",
                latitude = "-16.398500",
                address = "General Morán 114, Arequipa, Peru"
            ),
            TouristicPlaceDto(
                name = "Cayma Viewpoint",
                description = "A viewpoint that provides stunning views of the Chili River and surrounding landscape.",
                longitude = "-71.548300",
                latitude = "-16.385200",
                address = "Cayma, Arequipa, Peru"
            ),
            TouristicPlaceDto(
                name = "Main Square of Arequipa",
                description = "The vibrant main square surrounded by important historical buildings and restaurants.",
                longitude = "-71.536300",
                latitude = "-16.398300",
                address = "Plaza de Armas, Arequipa, Peru"
            )
        )

        for (place in sampleTouristicPlaces) {
            try {
                db.collection(DBCollection.TOURISTIC_PLACE)
                    .add(place)
                    .await()
            } catch (e: Exception) {
                Log.d("SEED", "something went wrong")
            }
        }
    }
}
