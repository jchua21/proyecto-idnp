package com.jean.touraqp.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.domain.authentication.LogInUserUseCase
import com.jean.touraqp.auth.domain.authentication.model.toUserUI
import com.jean.touraqp.auth.domain.validation.ValidateEmailUseCase
import com.jean.touraqp.auth.domain.validation.ValidatePasswordUseCase
import com.jean.touraqp.auth.domain.validation.ValidationResult
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.core.utils.ResourceResult
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
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val logInUserUseCase: LogInUserUseCase,
) : ViewModel() {

    //Error Validation messages
    private val _state = MutableStateFlow(LoginUserState())
    val state = _state.asStateFlow()

    private val _effect = Channel<LoginFormEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }
            is LoginFormEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }
            LoginFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        //Validate
        val emailResult = validateEmailUseCase.execute(_state.value.email)
        val passwordResult = validatePasswordUseCase.execute(_state.value.password)
        val validationResult = listOf(emailResult, passwordResult)

        val hasValidationErrors = hasValidationErrors(validationResult)

        _state.update {
            it.copy(
                emailError = (emailResult as? ValidationResult.ErrorResult)?.message,
                passwordError = (passwordResult as? ValidationResult.ErrorResult)?.message
            )
        }
         if(hasValidationErrors) return

        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }

            logInUserUseCase.execute(email = _state.value.email, password = _state.value.password)
                .onSuccess { user ->
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _effect.send(
                        LoginFormEffect.OnSuccessUserLogin(user.toUserUI())
                    )
//                    // Update User Session

                }
                .onError {
                    _state.update {
                        it.copy(isLoading = false)
                    }

                    _effect.send(
                        LoginFormEffect.OnErrorUserLogin(it.message ?: "Something went wrong")
                    )
                }
        }
    }

    private fun hasValidationErrors(validationsResult: List<ValidationResult>): Boolean {
        return validationsResult.any() { it is ValidationResult.ErrorResult }
    }
}