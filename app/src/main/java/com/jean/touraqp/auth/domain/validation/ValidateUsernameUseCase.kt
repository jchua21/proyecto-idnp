package com.jean.touraqp.auth.domain.validation

import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {

    companion object {
        const val MIN_REQUIRED_LENGTH = 4
    }

    fun execute(username: String): ValidationResult {
        if (username.isEmpty()) {
            return ValidationResult.ErrorResult(message = "El nombre de usuario no puede estar vacio")
        }
        if (username.length < MIN_REQUIRED_LENGTH){
            return ValidationResult.ErrorResult(message = "El nombre de usuario debe de tener como minimo $MIN_REQUIRED_LENGTH caracteres")
        }

        return ValidationResult.SuccessfulResult
    }

}