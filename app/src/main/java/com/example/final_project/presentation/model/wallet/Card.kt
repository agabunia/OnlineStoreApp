package com.example.final_project.presentation.model.wallet

data class Card(
    val id: String,
    val cardNumber: String,
    val date: String,
    val cvv: String,
    val cardType: CardType
) {
    enum class CardType {
        VISA,
        MASTER_CARD,
        AMERICAN_EXPRESS,
        OTHER
    }
}
