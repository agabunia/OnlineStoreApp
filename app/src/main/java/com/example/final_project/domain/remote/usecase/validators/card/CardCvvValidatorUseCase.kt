package com.example.final_project.domain.remote.usecase.validators.card

class CardCvvValidatorUseCase {
    operator fun invoke(cvv: String): Boolean {
        val cvvToInt = cvv.toIntOrNull()
        return cvv.length == 3 && cvvToInt != null
    }
}