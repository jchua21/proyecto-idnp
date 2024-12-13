package com.jean.touraqp.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.domain.authentication.RegisterUserUseCase
import com.jean.touraqp.auth.domain.authentication.model.toUserUI
import com.jean.touraqp.auth.domain.validation.ValidateConfirmPasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateEmailUseCase
import com.jean.touraqp.auth.domain.validation.ValidateNameUseCase
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateUsernameUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.auth.presentation.model.toUser
import com.jean.touraqp.core.utils.onError
import com.jean.touraqp.core.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    //Validation State
    private val _state = MutableStateFlow(RegisterUserState())
    val state = _state.asStateFlow()

    // One-time events
    private val _effect = Channel<RegisterFormEffect>()
    val effect = _effect.receiveAsFlow()


    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.ConfirmPasswordChanged -> {
                _state.update {
                    it.copy(confirmPassword = event.confirmPassword)
                }
            }

            is RegisterFormEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            is RegisterFormEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is RegisterFormEvent.NameChanged -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }

            is RegisterFormEvent.UsernameChanged -> {
                _state.update {
                    it.copy(username = event.username)
                }
            }

            RegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {

        val usernameResult = validateUsernameUseCase.execute(_state.value.username)
        val nameResult = validateNameUseCase.execute(_state.value.name)
        val emailResult = validateEmailUseCase.execute(_state.value.email)
        val passwordResult = validatePasswordUseCase.execute(_state.value.password)
        val confirmPasswordResult = validateConfirmPasswordUseCase.execute(
            _state.value.password,
            _state.value.confirmPassword
        )

        val validationResult =
            listOf(usernameResult, nameResult, emailResult, passwordResult, confirmPasswordResult)

        val hasValidationErrors = hasValidationErrors(validationResult)

        _state.update {
            it.copy(
                usernameError = (usernameResult as? ValidationResult.ErrorResult)?.message,
                nameError = (nameResult as? ValidationResult.ErrorResult)?.message,
                emailError = (emailResult as? ValidationResult.ErrorResult)?.message,
                passwordError = (passwordResult as? ValidationResult.ErrorResult)?.message,
                confirmPasswordError = (confirmPasswordResult as? ValidationResult.ErrorResult)?.message,
            )
        }

        if (hasValidationErrors) return
        // Register User
        val userData = UserUI(
            username = _state.value.username,
            name = _state.value.name,
            email = _state.value.email,
            password = _state.value.password
        )

        viewModelScope.launch(Dispatchers.IO)
        {
            _state.update {
                it.copy(isLoading = true)
            }
            registerUserUseCase.execute(userData.toUser())
                .onSuccess { user ->
                    _state.update {
                        it.copy(isLoading = false)
                    }

                    val userUI = user.toUserUI()
                    _effect.send(
                        RegisterFormEffect.OnSuccessUserRegistered(userUI)
                    )
                }
                .onError { error ->
                    _effect.send(
                        RegisterFormEffect.OnErrorUserRegistered(
                            error.message ?: "Something went wrong"
                        )
                    )
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
        }
    }

    private fun hasValidationErrors(validationsResult: List<ValidationResult>): Boolean {
        return validationsResult.any() { it is ValidationResult.ErrorResult }
    }
}