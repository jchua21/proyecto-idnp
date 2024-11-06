package com.jean.touraqp.auth.ui.login

import android.os.Bundle
import android.util.Log
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
                    loginViewModel.loginValidationState.collect() { validationErrors ->
                        loginScreenBinding?.apply {
                            inputTextUsername.error = validationErrors.emailError
                            inputTextPassword.error = validationErrors.passwordError
                        }
                    }
                }
                launch {
                    loginViewModel.operationResulChannel.collect() { loginResult ->
                        loginScreenBinding?.btnLogin?.isEnabled = !loginResult.isLoading
                        Toast.makeText(context, loginResult.resultMessage.orEmpty(), Toast.LENGTH_SHORT)
                            .show()

                        if(loginResult.isSuccess){
                            findNavController().navigate(R.id.action_global_core_graph)
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