package com.jean.touraqp.auth.presentation.login

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
import com.jean.touraqp.databinding.FragmentLoginScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginScreenFragment : Fragment(R.layout.fragment_login_screen) {

    private var loginScreenBinding: FragmentLoginScreenBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginScreenBinding.bind(view)
        loginScreenBinding = binding

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        loginScreenBinding?.apply {
            btnGoToRegister.setOnClickListener() {
                findNavController().navigate(R.id.action_loginScreenFragment_to_registerScreenFragment)
            }
            btnLogin.setOnClickListener() {
                loginViewModel.onEvent(LoginFormEvent.Submit)
            }
            inputTextUsername.doOnTextChanged { text, _, _, _ ->
                loginViewModel.onEvent(LoginFormEvent.EmailChanged(text.toString()))
            }
            inputTextPassword.doOnTextChanged { text, _, _, _ ->
                loginViewModel.onEvent(LoginFormEvent.PasswordChanged(text.toString()))
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    loginViewModel.state.collect() { state ->
                        loginScreenBinding?.apply {
                            inputTextUsername.error = state.emailError
                            inputTextPassword.error = state.passwordError
                            btnLogin.isEnabled = !state.isLoading
                        }
                    }
                }
                launch {
                    loginViewModel.effect.collect() { effect ->
                        when(effect){
                            is LoginFormEffect.OnErrorUserLogin -> {
                                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT)
                                    .show()

                            }
                            LoginFormEffect.OnSuccessUserLogin -> {
                                findNavController().navigate(R.id.action_global_core_graph)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginScreenBinding = null
    }
}