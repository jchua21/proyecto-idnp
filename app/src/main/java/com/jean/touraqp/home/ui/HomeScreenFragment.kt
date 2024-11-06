package com.jean.touraqp.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jean.touraqp.R
import com.jean.touraqp.core.UserSession
import com.jean.touraqp.databinding.FragmentHomeScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private var homeScreenBinding : FragmentHomeScreenBinding? = null
    @Inject
    lateinit var userSession: UserSession

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeScreenBinding = FragmentHomeScreenBinding.bind(view)

//        homeScreenBinding?.userUsername?.text = userSession.username
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeScreenBinding = null
    }

}