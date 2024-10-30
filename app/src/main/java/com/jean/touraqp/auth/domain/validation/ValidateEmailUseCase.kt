package com.jean.touraqp.auth.domain.validation

import android.util.Patterns
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isEmpty()) {
            return ValidationResult.ErrorResult(message = "El email no puede estar vacio")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult.ErrorResult(message = "El email debe de ser valido")
        }

        return ValidationResult.SuccessfulResult
    }

}