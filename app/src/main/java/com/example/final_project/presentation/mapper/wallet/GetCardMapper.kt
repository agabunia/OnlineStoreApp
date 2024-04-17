package com.example.final_project.presentation.mapper.wallet

import com.example.final_project.domain.remote.model.firebase_cloud_store.card.GetCard
import com.example.final_project.presentation.model.wallet.Card

fun GetCard.toPresenter(): Card {
    val cardType = when (cardType) {
        GetCard.GetCardType.VISA -> Card.CardType.VISA
        GetCard.GetCardType.MASTER_CARD -> Card.CardType.MASTER_CARD
        GetCard.GetCardType.AMERICAN_EXPRESS -> Card.CardType.AMERICAN_EXPRESS
        GetCard.GetCardType.OTHER -> Card.CardType.OTHER
    }

    return Card(
        id = id,
        cardNumber = cardNumber,
        date = date,
        cvv = cvv,
        cardType = cardType
    )
}

fun Card.toDomain(): GetCard {
    val cardType = when (cardType) {
        Card.CardType.VISA -> GetCard.GetCardType.VISA
        Card.CardType.MASTER_CARD -> GetCard.GetCardType.MASTER_CARD
        Card.CardType.AMERICAN_EXPRESS -> GetCard.GetCardType.AMERICAN_EXPRESS
        Card.CardType.OTHER -> GetCard.GetCardType.OTHER
    }

    return GetCard(
        id = id,
        cardNumber = cardNumber,
        date = date,
        cvv = cvv,
        cardType = cardType
    )
}