package com.example.final_project.presentation.event.payment

sealed class PaymentEvent {
    data class ProcessPayment(val isSuccessful: Boolean): PaymentEvent()
    object NavigateToHome: PaymentEvent()
    object NavigateBack: PaymentEvent()
}