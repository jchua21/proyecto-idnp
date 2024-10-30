package com.jean.touraqp.auth.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateUsernameUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _loginInputState = MutableStateFlow(LoginInputState())
    val loginInputState = _loginInputState.asStateFlow()

    private val _loginValidationState = MutableStateFlow(LoginValidationState())
    val loginValidationState = _loginValidationState.asStateFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.PasswordChanged -> {
                _loginInputState.value = _loginInputState.value.copy(password = event.password)
            }

            is LoginFormEvent.UsernameChanged -> {
                _loginInputState.value = _loginInputState.value.copy(username = event.username)
            }

            LoginFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        //Validate
        val (username, password) = _loginInputState.value
        Log.d("values", "$username!!!")
        Log.d("values", "$password!!!")
        val usernameResult = validateUsernameUseCase.execute(username = username)
        val passwordResult = validatePasswordUseCase.execute(password = password)

        val hasError =
            listOf(usernameResult, passwordResult).any() { it is ValidationResult.ErrorResult }


        if (hasError) {
            _loginValidationState.value = _loginValidationState.value.copy(
                usernameError = (usernameResult as? ValidationResult.ErrorResult)?.message,
                passwordError = (passwordResult as? ValidationResult.ErrorResult)?.message
            )
            return
        }

        //TODO
        Log.d("SUBMIT", "LOGGIN!!!")
    }

}