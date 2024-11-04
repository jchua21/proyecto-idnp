package com.jean.touraqp.auth.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.domain.authentication.LogInUserUseCase
import com.jean.touraqp.auth.domain.validation.ValidateEmailUseCase
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import com.jean.touraqp.auth.ui.model.UserUI
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.core.UserSession
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
    private val logInUserUseCase: LogInUserUseCase,
    private val userSession: UserSession
) : ViewModel() {

    // Input State
    private var userUI = UserUI()

    //Error Validation messages
    private val _loginValidationState = MutableStateFlow(LoginValidationState())
    val loginValidationState = _loginValidationState.asStateFlow()

    private val _operationResultChannel = Channel<LoginResultState>()
    val operationResulChannel = _operationResultChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.PasswordChanged -> {
                userUI = userUI.copy(password = event.password)
            }

            is LoginFormEvent.EmailChanged -> {
                userUI = userUI.copy(email = event.email)
            }

            LoginFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        //Validate
        val (_, _, _, email, password) = userUI

        val validationErrors = validateInputs(email, password)
        if (validationErrors != null) {
            _loginValidationState.value = validationErrors
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            logInUserUseCase.execute(email = email, password = password).collect() { result ->
                when (result) {
                    is ResourceResult.Error -> {
                        _operationResultChannel.send(LoginResultState(resultMessage = result.message))
                    }

                    is ResourceResult.Loading -> {
                        _operationResultChannel.send(
                            LoginResultState(
                                isLoading = true,
                                resultMessage = "Loading..."
                            )
                        )
                    }

                    is ResourceResult.Success -> {
                        val user = result.data!!
                        val message = result.message

                        _operationResultChannel.send(
                            LoginResultState(
                                isSuccess = true,
                                user = user,
                                resultMessage = message
                            )
                        )
                        // Update User Session
                        userSession.updateSession(
                            id = user.id!!,
                            name = user.name,
                            email = user.email,
                            username = user.username
                        )
                    }
                }
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