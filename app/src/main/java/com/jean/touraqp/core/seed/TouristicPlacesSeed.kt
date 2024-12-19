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
                name = "Molino de Sabandía",
                description = "El Molino de Sabandía, construido en 1621, es un símbolo de la ingeniería hidráulica colonial y un lugar de gran valor histórico en Arequipa. Este molino, rodeado de vegetación y pequeñas cascadas, permite a los visitantes viajar al pasado y conocer cómo se procesaban los granos utilizando la fuerza del agua. Además, la belleza natural del entorno y su importancia arquitectónica hacen de este sitio un destino ideal para aquellos que buscan combinar historia y naturaleza.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24pI7nnb2xuWhQoerYG5dpSOyBz0FjltEsgafH",
                longitude = -71.4995798,
                latitude = -16.4567451,
                address = "Sabandía, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Museo de Arequipa",
                description = "El Museo de Arequipa es un espacio que alberga una valiosa colección de artefactos que cuentan la historia de la ciudad desde sus raíces prehispánicas hasta la época colonial. Con exposiciones que muestran piezas de cerámica, textiles antiguos, y documentos históricos, el museo ofrece una experiencia educativa enriquecedora. Este lugar es perfecto para quienes desean profundizar en la rica historia y el legado cultural de Arequipa.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24MKmLzdWKIev02bVpzgQ1hBNZTOXayEiR3Yq7",
                longitude = -71.541448,
                latitude = -16.3961094,
                address = "Calle Santa Catalina, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Monasterio de Santa Catalina",
                description = "El Monasterio de Santa Catalina es un conjunto monumental que se extiende a lo largo de 20,000 metros cuadrados y es considerado uno de los mayores atractivos de Arequipa. Fundado en 1579, este convento es un verdadero laberinto de calles adoquinadas, patios coloridos y celdas que alguna vez albergaban a las monjas de clausura. Sus muros, pintados de intensos colores y decorados con obras de arte religioso, cuentan historias de devoción y reclusión en un espacio lleno de misticismo.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24eedqYYFoztHEMrIK9fTnlAiOG37U4VdFJNys",
                longitude = -71.541448,
                latitude = -16.3961094,
                address = "Santa Catalina 301, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Cueva de Sumbay",
                description = "La Cueva de Sumbay, conocida por sus pinturas rupestres, es un tesoro arqueológico que revela la vida de los primeros habitantes de la región andina hace miles de años. Las representaciones en las paredes muestran figuras humanas y de animales en escenas de caza, destacando la relación entre el hombre y la naturaleza en la época prehistórica. Este sitio es perfecto para los amantes de la arqueología y la aventura, rodeado de un paisaje rocoso y deslumbrante.",
                longitude = -71.3597186,
                latitude = -15.9757002,
                address = "Sumbay, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "El Cañón del Colca",
                description = "El Cañón del Colca, uno de los cañones más profundos del mundo, es un destino impresionante que combina naturaleza y cultura. Con más de 3,400 metros de profundidad, ofrece vistas espectaculares y la oportunidad de observar al majestuoso cóndor andino en su hábitat natural. Las terrazas agrícolas que bordean el cañón, heredadas de las civilizaciones preincaicas, son un testimonio de la ingeniosa adaptación humana al entorno montañoso. Visitar el Colca es una experiencia que permite conectar con la historia, la belleza paisajística y las tradiciones vivas de sus comunidades.",
                longitude = -72.1820172,
                latitude = -15.6574462,
                address = "Cañón del Colca, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Mirador de Yanahuara",
                description = "El Mirador de Yanahuara es uno de los lugares más icónicos de Arequipa, famoso por sus arcos de sillar grabados con frases célebres de personajes históricos y por ofrecer una vista panorámica espectacular de la ciudad y del imponente volcán Misti. Este mirador, rodeado de calles empedradas y casas coloniales, es un espacio donde se respira tranquilidad y se puede apreciar la majestuosidad de la arquitectura y la naturaleza de Arequipa.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24dpqf4pwA0gin2dHAV89k4PWEMefKrjO1mR3Z",
                longitude = -71.5443023,
                latitude = -16.3874759,
                address = "Yanahuara, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Museo Santuarios Andinos",
                description = "El Museo Santuarios Andinos es famoso por resguardar a 'Juanita', la momia congelada mejor conservada de América, también conocida como la 'Dama de Ampato'. Esta momia, descubierta en las alturas del volcán Ampato, es un vestigio de los rituales incas de sacrificio. El museo ofrece una inmersión en las costumbres y creencias incas, mostrando objetos ceremoniales, tejidos y explicaciones detalladas sobre la importancia de la montaña y los rituales sagrados.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24zktXua5gLZai7kVsJHjbrE5mFqdhepBCK1OD",
                longitude = -71.5403299,
                latitude = -16.3999611,
                address = "La Merced 110, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Plaza de Armas de Arequipa",
                description = "La Plaza de Armas de Arequipa es el centro neurálgico y un símbolo de la identidad de la ciudad. Rodeada por la Catedral y otras edificaciones coloniales, la plaza se caracteriza por sus elegantes portales y jardines bien cuidados. Es un lugar lleno de vida, donde se celebran eventos culturales y se puede observar la cotidianidad de los arequipeños mientras se disfruta del imponente paisaje que ofrecen los volcanes circundantes.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24Z9SOpZQ4wQdVtaSrGMYB9HzOIsZEnxoq2F1P",
                longitude = -71.5394728,
                latitude = -16.398829,
                address = "Plaza de Armas, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Barrio de San Lázaro",
                description = "El Barrio de San Lázaro es conocido como el rincón más antiguo de Arequipa, con sus estrechas calles de piedra y pasajes que transportan a los visitantes a la época colonial. Este barrio, con sus pequeñas plazas y arquitectura de sillar, es un espacio de tranquilidad y de gran encanto, ideal para paseos a pie y para conocer la historia que forma la esencia de Arequipa.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp249U7qHUEaSNBEr72t1esmRFLfVpDxianZKcwT",
                longitude = -71.5352373,
                latitude = -16.3932582,
                address = "San Lázaro, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Claustros de la Compañía",
                description = "Los Claustros de la Compañía son una obra maestra de la arquitectura colonial en Arequipa. Construidos en el siglo XVII, estos claustros de sillar destacan por sus arcos bellamente tallados y por su historia vinculada a la orden jesuita. Hoy en día, albergan tiendas y exposiciones de arte, convirtiéndose en un espacio vibrante donde se mezclan el pasado y el presente de la ciudad.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24BxeRsqcbZeKwBCQv46Op0n7ul8R5axos2rAy",
                longitude = -71.5388992,
                latitude = -16.4001903,
                address = "Calle General Morán, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Las Caleras de Yura",
                description = "Las Caleras de Yura son un conjunto de baños termales naturales ubicados a las afueras de Arequipa. Con aguas ricas en minerales y propiedades relajantes, estas termas han sido un destino popular para quienes buscan descanso y bienestar desde tiempos antiguos. Rodeadas de montañas y un paisaje sereno, Las Caleras ofrecen una experiencia única para desconectarse y revitalizar el cuerpo y la mente.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24npOyG21SnN5sjg2UVekRO4C9Mi7zpY0FfJma",
                longitude = -71.6980061,
                latitude = -16.2458375,
                address = "Yura, Arequipa, Perú"
            ),
            TouristicPlaceDto(
                name = "Casa del Moral",
                description = "Las Caleras de Yura son un conjunto de baños termales naturales ubicados a las afueras de Arequipa. Con aguas ricas en minerales y propiedades relajantes, estas termas han sido un destino popular para quienes buscan descanso y bienestar desde tiempos antiguos. Rodeadas de montañas y un paisaje sereno, Las Caleras ofrecen una experiencia única para desconectarse y revitalizar el cuerpo y la mente.",
                imageUrl = "https://utfs.io/f/AFyLEdVPOp24npOyG21SnN5sjg2UVekRO4C9Mi7zpY0FfJma",
                longitude = -71.5378812,
                latitude = -16.3969042,
                address = "Calle Moral 318, Arequipa, Perú"
            )
        )


        for (place in sampleTouristicPlaces) {
            try {
                db.collection(DBCollection.TOURISTIC_PLACE)
                    .add(place)
                    .await()
                Log.d("SEED", "Successfully added")

            } catch (e: Exception) {
                Log.d("SEED", "something went wrong: ${e.message}")
            }
        }
    }
}
