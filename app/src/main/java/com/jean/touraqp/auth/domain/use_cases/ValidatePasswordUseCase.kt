package com.jean.touraqp.auth.domain.use_cases

import android.util.Log
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(){

    companion object {
        const val MIN_REQUIRED_LENGTH = 4
    }

    fun execute(password: String): ValidationResult {
        Log.d("VALIDATE", "initListeners: ${password} ")

        if (password.isEmpty()) {
            return ValidationResult.ErrorResult(message = "La contrasena no puede estar vacio")
        }

        val containsLetterAndDigits = password.any() { it.isDigit() } && password.any() {it.isLetter()}
        if (!containsLetterAndDigits) {
            return ValidationResult.ErrorResult(message = "La contrasena debe de tener por lo menos un digito y una letra")
        }

        return ValidationResult.SuccessfulResult
    }

}