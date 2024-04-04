package com.example.final_project.domain.remote.usecase.payment

class PaymentUseCase {
    private val totalAmount = 5000

    operator fun invoke(amount: Int): Boolean {
        return amount <= totalAmount
    }

}