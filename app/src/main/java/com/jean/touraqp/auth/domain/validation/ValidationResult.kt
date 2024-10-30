package com.jean.touraqp.auth.domain.validation

sealed class ValidationResult{
    object SuccessfulResult: ValidationResult(){}
    data class ErrorResult(val message: String): ValidationResult()
}