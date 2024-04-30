package com.example.final_project.domain.remote.usecase.validators.card

import java.util.Calendar

class CardDateValidatorUseCase {
    operator fun invoke(date: String): Boolean {
        if (date.length != 5) return false

        val cardMonth = date.take(2).toIntOrNull()
        val cardYear = date.takeLast(2).toIntOrNull()
        if (cardMonth == null || cardYear == null) return false
        val adjustedCardYear = cardYear + 2000

        val current = Calendar.getInstance()
        val currentMonth = current.get(Calendar.MONTH) + 1
        val currentYear = current.get(Calendar.YEAR)

        return if (adjustedCardYear > currentYear) {
            true
        } else if (adjustedCardYear == currentYear && cardMonth >= currentMonth) {
            true
        } else {
            false
        }
    }
}