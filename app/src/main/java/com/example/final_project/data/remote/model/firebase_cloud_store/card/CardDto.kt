package com.example.final_project.data.remote.model.firebase_cloud_store.card

data class CardDto(
    val id: String,
    val cardNumber: String,
    val date: String,
    val cvv: String,
    val cardType: CardTypeDto
) {
    enum class CardTypeDto {
        VISA,
        MASTER_CARD,
        AMERICAN_EXPRESS,
        OTHER
    }
}
