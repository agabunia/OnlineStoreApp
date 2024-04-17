package com.example.final_project.data.remote.mapper.firebase_cloud_store.card

import com.example.final_project.data.remote.model.firebase_cloud_store.card.CardDto
import com.example.final_project.domain.remote.model.firebase_cloud_store.card.GetCard

fun CardDto.toDomain(): GetCard {
    val getCardType = when (cardType) {
        CardDto.CardTypeDto.VISA -> GetCard.GetCardType.VISA
        CardDto.CardTypeDto.MASTER_CARD -> GetCard.GetCardType.MASTER_CARD
        CardDto.CardTypeDto.AMERICAN_EXPRESS -> GetCard.GetCardType.AMERICAN_EXPRESS
        CardDto.CardTypeDto.OTHER -> GetCard.GetCardType.OTHER
    }

    return GetCard(
        id = id,
        cardNumber = cardNumber,
        date = date,
        cvv = cvv,
        cardType = getCardType
    )
}

fun GetCard.toData(): CardDto {
    val cardTypeDto = when (cardType) {
        GetCard.GetCardType.VISA -> CardDto.CardTypeDto.VISA
        GetCard.GetCardType.MASTER_CARD -> CardDto.CardTypeDto.MASTER_CARD
        GetCard.GetCardType.AMERICAN_EXPRESS -> CardDto.CardTypeDto.AMERICAN_EXPRESS
        GetCard.GetCardType.OTHER -> CardDto.CardTypeDto.OTHER
    }

    return CardDto(
        id = id,
        cardNumber = cardNumber,
        date = date,
        cvv = cvv,
        cardType = cardTypeDto
    )
}