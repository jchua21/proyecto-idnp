package com.jean.touraqp.auth.domain.validation

import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor() {

    fun execute(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult.ErrorResult(message = "Las contrasenas no coinciden")
        }

        return ValidationResult.SuccessfulResult
    }

}