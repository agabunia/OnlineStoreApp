package com.example.final_project.domain.remote.usecase.validators.card

class CardNumberValidatorUseCase {
    operator fun invoke(cardNumber: String): Boolean {
        return if(cardNumber[0] == '0') {
            false
        } else if (cardNumber.length != 19) {
            false
        } else {
            true
        }
    }
}