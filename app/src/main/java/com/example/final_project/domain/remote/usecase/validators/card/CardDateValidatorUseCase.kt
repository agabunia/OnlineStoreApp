package com.example.final_project.domain.remote.usecase.validators.card

import java.time.LocalDate
import java.util.Calendar

class CardDateValidatorUseCase {
    operator fun invoke(date: String): Boolean {
        val month = date.take(2).toInt()
        val year = date.takeLast(2).toInt()

        val current = Calendar.getInstance()
        val currentMonth = current.get(Calendar.MONTH)
        val currentYear = current.get(Calendar.YEAR)

        return if (date.length != 5) {
            false
        } else if (month > currentMonth || year > currentYear) {
            false
        } else {
            true
        }
    }
}