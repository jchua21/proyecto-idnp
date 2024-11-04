package com.jean.touraqp.auth.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.domain.authentication.SignUpUserUseCase
import com.jean.touraqp.auth.domain.validation.ValidateConfirmPasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateEmailUseCase
import com.jean.touraqp.auth.domain.validation.ValidateNameUseCase
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateUsernameUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import com.jean.touraqp.auth.ui.model.UserUI
import com.jean.touraqp.auth.ui.model.toUserDomain
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
class RegisterViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val signUpUserUseCase: SignUpUserUseCase,
) : ViewModel() {

    //User model
    private var userUI = UserUI()

    //Validation State
    private val _registrationValidationState = MutableStateFlow(RegisterValidationState())
    val registrationValidationState = _registrationValidationState.asStateFlow()

    // One-time events
    private val _resultChannel = Channel<RegisterResultState>()
    val resultChannel = _resultChannel.receiveAsFlow()


    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.ConfirmPasswordChanged -> {
                userUI = userUI.copy(confirmPassword = event.confirmPassword)
            }
            is RegisterFormEvent.PasswordChanged -> {
                userUI = userUI.copy(password = event.password)
            }
            is RegisterFormEvent.EmailChanged -> {
                userUI = userUI.copy(email = event.email)
            }
            is RegisterFormEvent.NameChanged -> {
                userUI = userUI.copy(name = event.name)
            }
            is RegisterFormEvent.UsernameChanged -> {
                userUI = userUI.copy(username = event.username)
            }
            RegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val (_,username, name, email, password, confirmPassword) = userUI

        val validationErrors = validateInputs(
            username = username,
            name = name,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )

        if (validationErrors != null) {
            _registrationValidationState.value = validationErrors
            return
        }

        // Register User
        val userData = UserUI(username = username, name = name, email = email, password = password)

        viewModelScope.launch(Dispatchers.IO) {
            signUpUserUseCase.execute(userData.toUserDomain()).collect() { result ->
                when (result) {
                    is ResourceResult.Error -> {
                        _resultChannel.send(RegisterResultState(resultMessage = result.message))
                    }

                    is ResourceResult.Loading -> {
                        _resultChannel.send(
                            RegisterResultState(
                                resultMessage = "Loading!!!",
                                isLoading = true
                            )
                        )
                    }
                    is ResourceResult.Success -> {
                        val user = result.data!!
                        val message = result.message

                        _resultChannel.send(
                            RegisterResultState(
                                isSuccess = true,
                                resultMessage = message,
                                user = user
                            )
                        )

                    }
                }
            }
        }
    }

    private fun validateInputs(
        username: String,
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): RegisterValidationState? {
        val usernameResult = validateUsernameUseCase.execute(username)
        val nameResult = validateNameUseCase.execute(name)
        val emailResult = validateEmailUseCase.execute(email)
        val passwordResult = validatePasswordUseCase.execute(password)
        val confirmPasswordResult = validateConfirmPasswordUseCase.execute(
            confirmPassword,
            password
        )
        val hasError =
            listOf(
                usernameResult,
                nameResult,
                emailResult,
                passwordResult,
                confirmPasswordResult
            ).any() { it is ValidationResult.ErrorResult }

        if (hasError) {
            //It only emits values when there is a change, which can prevent unnecessary updates in your UI when the same error messages are still present -> StateFlow.
            return RegisterValidationState(
                usernameError = (usernameResult as? ValidationResult.ErrorResult)?.message,
                nameError = (nameResult as? ValidationResult.ErrorResult)?.message,
                emailError = (emailResult as? ValidationResult.ErrorResult)?.message,
                passwordError = (passwordResult as? ValidationResult.ErrorResult)?.message,
                confirmPasswordError = (confirmPasswordResult as? ValidationResult.ErrorResult)?.message,
            )
        }
        return null
    }
}