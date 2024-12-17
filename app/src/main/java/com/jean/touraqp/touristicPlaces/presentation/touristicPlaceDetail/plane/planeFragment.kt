package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.plane

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.jean.touraqp.R

class planeFragment : Fragment(), PlanoCoordenadas.OnAreaClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Infla el diseño del fragmento
        return inflater.inflate(R.layout.fragment_plane, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Configura el PlanoView y define el listener
        val planoView = view.findViewById<PlanoCoordenadas>(R.id.Plano)
        planoView.setOnAreaClickListener(this)
    }

    override fun onAreaClicked(areaName: String) {
        // Reemplaza el contenedor por el fragmento correspondiente
        val fragment = AreaFragment().apply {
            arguments = Bundle().apply {
                putString("areaName", areaName)
            }
        }

        parentFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
}