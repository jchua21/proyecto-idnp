package com.jean.touraqp.auth.presentation.register

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
                    registerViewModel.state.collect() { state ->
                        registerScreenBinding?.apply {
                            editTextUsername.error = state.usernameError
                            editTextName.error = state.nameError
                            editTextEmail.error = state.emailError
                            editTextPassword.error = state.passwordError
                            editTextConfirmPassword.error = state.confirmPasswordError
                            btnRegister.isEnabled = !state.isLoading
                        }
                    }
                }
                launch {
                    registerViewModel.effect.collect() { effect ->
                        when(effect){
                            is RegisterFormEffect.OnErrorUserRegistered -> {
                                Toast.makeText(context, effect.message , Toast.LENGTH_SHORT)
                                    .show()
                            }
                            RegisterFormEffect.OnSuccessUserRegistered -> {
                                Toast.makeText(context, "Authenticated Successfully", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().navigate(R.id.action_global_core_graph)
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