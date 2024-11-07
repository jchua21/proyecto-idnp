package com.jean.touraqp.touristicPlaces.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentSearchScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    private var fragmentSearchScreenBinding : FragmentSearchScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchScreenBinding = FragmentSearchScreenBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSearchScreenBinding = null
    }
}