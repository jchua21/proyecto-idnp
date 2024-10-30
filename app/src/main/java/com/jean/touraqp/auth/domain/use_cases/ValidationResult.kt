package com.jean.touraqp.auth.domain.use_cases

sealed class ValidationResult{
    object SuccessfulResult: ValidationResult(){}
    data class ErrorResult(val message: String): ValidationResult()
}