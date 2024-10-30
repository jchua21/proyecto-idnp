package com.jean.touraqp.auth.ui.view.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentRegisterScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterScreenFragment : Fragment(R.layout.fragment_register_screen) {
    private var registerScreenBinding: FragmentRegisterScreenBinding? = null

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterScreenBinding.bind(view)
        registerScreenBinding = binding

        initListeners()
        initObserves()
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registrationValidationState.collect(){
                    registerScreenBinding?.apply {
                        editTextUsername.error = it.usernameError
                        editTextName.error = it.nameError
                        editTextEmail.error = it.emailError
                        editTextPassword.error = it.passwordError
                        editTextConfirmPassword.error = it.confirmPasswordError
                    }
                }
            }
        }
    }



    private fun initListeners() {
        registerScreenBinding?.apply {
            btnToLogin.setOnClickListener() {
                findNavController().navigate(R.id.action_registerScreenFragment_to_loginScreenFragment)
            }

            editTextUsername.doOnTextChanged { text, start, before, count ->
                registerViewModel.onEvent(RegisterFormEvent.UsernameChanged(text.toString()))
            }

            editTextName.doOnTextChanged { text, start, before, count ->
                registerViewModel.onEvent(RegisterFormEvent.NameChanged(text.toString()))
            }

            editTextEmail.doOnTextChanged { text, start, before, count ->
                registerViewModel.onEvent(RegisterFormEvent.EmailChanged(text.toString()))
            }

            editTextPassword.doOnTextChanged { text, start, before, count ->
                registerViewModel.onEvent(RegisterFormEvent.PasswordChanged(text.toString()))
            }

            editTextConfirmPassword.doOnTextChanged { text, start, before, count ->
                registerViewModel.onEvent(RegisterFormEvent.ConfirmPasswordChanged(text.toString()))
            }

            btnRegister.setOnClickListener() {
                registerViewModel.onEvent(RegisterFormEvent.Submit)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerScreenBinding = null
    }
}