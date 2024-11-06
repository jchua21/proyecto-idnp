package com.jean.touraqp.core.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentCoreLayoutBinding

class CoreLayoutFragment : Fragment(R.layout.fragment_core_layout) {

    private var mainLayoutBinding: FragmentCoreLayoutBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainLayoutBinding = FragmentCoreLayoutBinding.bind(view)
        // Find the NavController from the NavHostFragment
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.mainFragmentLayoutContainer) as NavHostFragment
        val navController = navHostFragment.navController
        mainLayoutBinding?.bottomNavigation?.setupWithNavController(navController)

        //This does not work
        //mainLayoutBinding?.bottomNavigation?.setupWithNavController(findNavController())
    }

}