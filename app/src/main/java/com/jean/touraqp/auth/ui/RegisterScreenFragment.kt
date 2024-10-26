package com.jean.touraqp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentLoginScreenBinding
import com.jean.touraqp.databinding.FragmentRegisterScreenBinding


class RegisterScreenFragment : Fragment(R.layout.fragment_register_screen) {
    private var registerScreenBinding : FragmentRegisterScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterScreenBinding.bind(view)
        registerScreenBinding = binding

        binding.btnToLogin.setOnClickListener(){
            findNavController().navigate(R.id.action_registerScreenFragment_to_loginScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerScreenBinding = null
    }
}