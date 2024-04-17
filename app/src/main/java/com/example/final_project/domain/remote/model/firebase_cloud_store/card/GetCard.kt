package com.example.final_project.domain.remote.model.firebase_cloud_store.card

data class GetCard(
    val id: String,
    val cardNumber: String,
    val date: String,
    val cvv: String,
    val cardType: GetCardType
) {
    enum class GetCardType {
        VISA,
        MASTER_CARD,
        AMERICAN_EXPRESS,
        OTHER
    }
}
