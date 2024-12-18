package com.jean.touraqp.core.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentCoreLayoutBinding

class CoreLayoutFragment : Fragment(R.layout.fragment_core_layout) {

    companion object {
        const val TAG = "core_layout"
    }

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

        mainLayoutBinding?.bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.searchScreenFragment -> {
                    Log.d(TAG, "searchFragment ")
                    navController.navigate(
                        R.id.searchScreenFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.searchScreenFragment, true)
                            .build()
                    )
                    true
                }
                R.id.profileScreenFragment -> {
                    Log.d(TAG, "profileFragment")

                    navController.navigate(
                        R.id.profileScreenFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.profileScreenFragment, true)
                            .build()
                    )
                    true
                }
                R.id.mapScreenFragment -> {
                    Log.d(TAG, "mapFragment")

                    navController.navigate(
                        R.id.mapScreenFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.mapScreenFragment, true)
                            .build()
                    )
                    true
                }
                else -> false
            }
        }
    }

}