package com.jean.touraqp.auth.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.domain.authentication.LogInUserUseCase
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.auth.domain.validation.ValidateEmailUseCase
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import com.jean.touraqp.core.ResourceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val logInUserUseCase: LogInUserUseCase
) : ViewModel() {

    // Input State
    private val _loginInputState = MutableStateFlow(LoginInputState())
    val loginInputState = _loginInputState.asStateFlow()

    //Error Validation messages
    private val _loginValidationState = MutableStateFlow(LoginValidationState())
    val loginValidationState = _loginValidationState.asStateFlow()

    private val _operationResultChannel = Channel<ResourceResult<User>>()
    val operationResulChannel = _operationResultChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.PasswordChanged -> {
                _loginInputState.value = _loginInputState.value.copy(password = event.password)
            }

            is LoginFormEvent.EmailChanged -> {
                _loginInputState.value = _loginInputState.value.copy(email = event.email)
            }

            LoginFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        //Validate
        val (email, password) = _loginInputState.value

        val validationErrors = validateInputs(email, password)
        if (validationErrors != null) {
            _loginValidationState.value = validationErrors
            return
        }
        viewModelScope.launch(Dispatchers.IO) {

            logInUserUseCase.execute(email = email, password = password).collect() {
                _operationResultChannel.send(it)
            }
        }
    }

    private fun validateInputs(
        email: String,
        password: String
    ): LoginValidationState? {
        val emailResult = validateEmailUseCase.execute(email)
        val passwordResult = validatePasswordUseCase.execute(password)

        val hasError =
            listOf(emailResult, passwordResult).any() { it is ValidationResult.ErrorResult }

        if (hasError) {
            return LoginValidationState(
                emailError = (emailResult as? ValidationResult.ErrorResult)?.message,
                passwordError = (passwordResult as? ValidationResult.ErrorResult)?.message,
            )
        }

        return null
    }

}