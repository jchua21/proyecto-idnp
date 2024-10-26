package com.jean.touraqp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentLoginScreenBinding

class LoginScreenFragment : Fragment(R.layout.fragment_login_screen) {

    private var loginScreenBinding : FragmentLoginScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginScreenBinding.bind(view)
        loginScreenBinding = binding

        binding.btnToRegister.setOnClickListener(){
            findNavController().navigate(R.id.action_loginScreenFragment_to_registerScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginScreenBinding = null
    }
}