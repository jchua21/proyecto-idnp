package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.plane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jean.touraqp.R


class AreaFragment : Fragment() {

    private var areaName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_area, container, false)

        // Obtén referencias a los elementos de UI
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val areaImageView: ImageView = view.findViewById(R.id.areaImageView)
        val closeButton: ImageButton = view.findViewById(R.id.closeButton)

        // Obtiene el nombre del área desde los argumentos
        areaName = arguments?.getString("areaName")

        // Configura la UI de acuerdo con el área seleccionada
        when (areaName) {
            "Calle Granada" -> {
                titleTextView.text = "Calle Granada"
                descriptionTextView.text =
                    "A esta calle se puede llegar desde la calle Burgos o desde la calle Sevilla, todas con nombres españoles dentro del recinto del Monasterio de Santa Catalina. Esta calle Granada en concreto es algo más ancha que otras y desde la que se entraba a la humeda y oscura cocina comunitaria del monasterio. Un poco más allá, unas escaleras llevan hasta la Plaza de Zocodover."
                areaImageView.setImageResource(R.drawable.calle_granada)
            }
            "Claustro Novicias" -> {
                titleTextView.text = "Claustro Novicias"
                descriptionTextView.text =
                    "Marcado por un árbol de caucho en su entrada, este era un área donde las monjas debían prestar un juramento de silencio y dedicar sus vidas a la oración y el trabajo. Las monjas servían como novicias durante 4 años, período en el cual sus familias tenían que pagar una dote de 100 monedas de oro por año. Al final de los 4 años, la novicia podía elegir entre entrar en una vida completa de servicio o dejar el convento (lo que acarrearía vergüenza a su familia)."
                areaImageView.setImageResource(R.drawable.claustro_novicias)
            }
            "Patio del Silencio" -> {
                titleTextView.text = "Patio del Silencio"
                descriptionTextView.text =
                    "El Patio del Silencio es el último tramo de transición desde el mundo exterior a la vida recogida entre estas rocas pesadas arrancadas con esfuerzo de la ladera del volcán. Su trazado en planta es algo más complejo que el anterior Patio de Labores, y está definido por dos cuadrados ligeramente desplazados a lo largo de su diagonal. Su función como espacio abierto es fundamental en la estructura organizativa del Convento de Santa Catalina."
                areaImageView.setImageResource(R.drawable.patio_del_silencio)
            }

            "Claustro de los Naranjos" -> {
                titleTextView.text = "Claustro de los Naranjos"
                descriptionTextView.text ="En el Monasterio de Santa Catalina, las novicias graduadas pasaban a alojarse en el patio de los Naranjos, todo pintando de azul y cuyo patio contiene precisamente eso árboles que le dan nombre: naranjos. Es en este claustro donde está la puerta que da acceso a la sala Profundis, que es donde se instalaba la capilla ardiente de las monjas."
                areaImageView.setImageResource(R.drawable.claustro_naranjos)
            }

            "Claustro Mayor" -> {
                titleTextView.text = "Claustro Mayor"
                descriptionTextView.text ="El patio central del monasterio, rodeado de arcos y decorado con coloridas flores, es un espacio tranquilo donde las monjas solían congregarse para la oración y el descanso."
                areaImageView.setImageResource(R.drawable.calustro_mayor)
            }
            "Pinacoteca" -> {
                titleTextView.text = "Pinacoteca"
                descriptionTextView.text ="Alberga una colección de arte religioso colonial, incluidas obras de la escuela cusqueña, que muestran la devoción y el simbolismo religioso de la época."
                areaImageView.setImageResource(R.drawable.pinacoteca)
            }

            "Coro Bajo" -> {
                titleTextView.text = "Coro Bajo"
                descriptionTextView.text ="Un área utilizada por las monjas para los cantos y rezos en comunidad, con antiguos asientos de madera y una atmósfera de recogimiento espiritual."
                areaImageView.setImageResource(R.drawable.coro_bajo)
            }

            "Iglesia Santa Catalina" -> {
                titleTextView.text = "Iglesia Santa Catalina"
                descriptionTextView.text ="La iglesia principal del monasterio, con una arquitectura sobria y altares dedicados a diferentes santos, donde se celebraban las misas y actos religiosos."
                areaImageView.setImageResource(R.drawable.iglesia_santa_catalina)
            }

            "Nuevo Monasterio" -> {
                titleTextView.text = "Nuevo Monasterio"
                descriptionTextView.text ="Construido en el siglo XVIII, esta zona ofrecía más privacidad a las monjas de clausura y presenta celdas individuales y áreas de retiro personal."
                areaImageView.setImageResource(R.drawable.nuevo_monasterio)
            }

            "Calle Toledo" -> {
                titleTextView.text = "Calle Toledo"
                descriptionTextView.text ="Una de las coloridas calles internas, con paredes de sillar pintadas de rojo, que recrea el ambiente de un típico pueblo colonial."
                areaImageView.setImageResource(R.drawable.calle_toledo)
            }
            "Calle Sevilla"-> {
                titleTextView.text = "Calle Sevilla"
                descriptionTextView.text ="Otra de las calles internas del monasterio, decorada en tonos azules, que conecta distintas áreas y añade al encanto pintoresco del lugar."
                areaImageView.setImageResource(R.drawable.calle_toledo)
            }
            "Calle Cordova"-> {
                titleTextView.text = "Calle Córdoba"
                descriptionTextView.text ="Un callejón interno de tonos ocre, rodeado de celdas y pequeños patios, que da la sensación de estar en una pequeña villa española."
                areaImageView.setImageResource(R.drawable.calle_toledo)
            }
            else -> {
                titleTextView.text = "Área Desconocida"
                descriptionTextView.text = "No se encontró información para esta área."
                areaImageView.setImageResource(R.drawable.defecto)
            }
        }

        // botón de cierre
        closeButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}