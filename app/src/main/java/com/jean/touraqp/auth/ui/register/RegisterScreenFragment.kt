package com.jean.touraqp.auth.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jean.touraqp.R
import com.jean.touraqp.core.ResourceResult
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
                launch {
                    registerViewModel.registrationValidationState.collect() {
                        registerScreenBinding?.apply {
                            editTextUsername.error = it.usernameError
                            editTextName.error = it.nameError
                            editTextEmail.error = it.emailError
                            editTextPassword.error = it.passwordError
                            editTextConfirmPassword.error = it.confirmPasswordError
                        }
                    }
                }
                launch {
                    registerViewModel.resultChannel.collect() {
                        when(it){
                            is ResourceResult.Error -> {
                                Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                                registerScreenBinding?.btnRegister?.isEnabled = true
                            }
                            is ResourceResult.Loading -> {
                                registerScreenBinding?.btnRegister?.isEnabled = false
                            }
                            is ResourceResult.Success -> {
                                Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                                registerScreenBinding?.btnRegister?.isEnabled = true
                            }
                        }
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

            editTextUsername.doOnTextChanged { text, _, _, _ ->
                registerViewModel.onEvent(RegisterFormEvent.UsernameChanged(text.toString()))
            }

            editTextName.doOnTextChanged { text, _, _, _ ->
                registerViewModel.onEvent(RegisterFormEvent.NameChanged(text.toString()))
            }

            editTextEmail.doOnTextChanged { text, _, _, _ ->
                registerViewModel.onEvent(RegisterFormEvent.EmailChanged(text.toString()))
            }

            editTextPassword.doOnTextChanged { text, _, _, _ ->
                registerViewModel.onEvent(RegisterFormEvent.PasswordChanged(text.toString()))
            }

            editTextConfirmPassword.doOnTextChanged { text, _, _, _ ->
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