package com.example.final_project.domain.remote.usecase.validators.card

class CardCvvValidatorUseCase {
    operator fun invoke(cvv: String): Boolean {
        return cvv.length <= 3
    }
}