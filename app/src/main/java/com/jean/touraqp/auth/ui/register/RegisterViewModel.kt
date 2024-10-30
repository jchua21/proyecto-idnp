package com.jean.touraqp.auth.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jean.touraqp.auth.domain.validation.ValidateConfirmPasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateEmailUseCase
import com.jean.touraqp.auth.domain.validation.ValidateNameUseCase
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidateUsernameUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase
) : ViewModel() {

    private val _registrationInputState = MutableStateFlow(RegisterInputState())
    val registrationInputState = _registrationInputState.asStateFlow()

    private val _registrationValidationState = MutableStateFlow(RegisterValidationState())
    val registrationValidationState = _registrationValidationState.asStateFlow()

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.ConfirmPasswordChanged -> {
                _registrationInputState.value =
                    registrationInputState.value.copy(confirmPassword = event.confirmPassword)
            }

            is RegisterFormEvent.PasswordChanged -> {
                _registrationInputState.value = registrationInputState.value.copy(password = event.password)
            }

            is RegisterFormEvent.EmailChanged -> {
                _registrationInputState.value = registrationInputState.value.copy(email = event.email)
            }

            is RegisterFormEvent.NameChanged -> {
                _registrationInputState.value = registrationInputState.value.copy(name = event.name)
            }

            is RegisterFormEvent.UsernameChanged -> {
                _registrationInputState.value = registrationInputState.value.copy(username = event.username)
            }

            RegisterFormEvent.Submit -> {
                submitData()
            }

        }
    }


    fun submitData() {
        val usernameResult = validateUsernameUseCase.execute(_registrationInputState.value.username)
        val nameResult = validateNameUseCase.execute(_registrationInputState.value.name)
        val emailResult = validateEmailUseCase.execute(_registrationInputState.value.email)
        val passwordResult = validatePasswordUseCase.execute(_registrationInputState.value.password)
        val confirmPasswordResult = validateConfirmPasswordUseCase.execute(
            _registrationInputState.value.confirmPassword,
            _registrationInputState.value.password
        )

        val hasError =
            listOf(
                usernameResult,
                nameResult,
                emailResult,
                passwordResult,
                confirmPasswordResult
            ).any(){it is ValidationResult.ErrorResult}

        if(hasError){
            _registrationValidationState.value = _registrationValidationState.value.copy(
                usernameError = (usernameResult as? ValidationResult.ErrorResult)?.message,
                nameError = (nameResult as? ValidationResult.ErrorResult)?.message,
                emailError = (emailResult as? ValidationResult.ErrorResult)?.message,
                passwordError = (passwordResult as? ValidationResult.ErrorResult)?.message,
                confirmPasswordError = (confirmPasswordResult as? ValidationResult.ErrorResult)?.message,
            )
            return
        }

        // Register User
        Log.d("SUBMIT", "Register User ")
    }

}