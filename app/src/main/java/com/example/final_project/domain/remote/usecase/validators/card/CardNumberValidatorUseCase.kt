package com.example.final_project.domain.remote.usecase.validators.card

class CardNumberValidatorUseCase {
    operator fun invoke(cardNumber: String): Boolean {
        val cardNumberToInt = cardNumber.toLongOrNull()
        return cardNumber.length == 16 && cardNumberToInt != null
    }
}