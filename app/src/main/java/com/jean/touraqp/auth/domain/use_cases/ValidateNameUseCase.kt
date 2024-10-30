package com.jean.touraqp.auth.domain.use_cases

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {

    fun execute(name: String): ValidationResult {
        if (name.isEmpty()) {
            return ValidationResult.ErrorResult(message = "Debe de ingresar su nombre")
        }
        return  ValidationResult.SuccessfulResult
    }

}