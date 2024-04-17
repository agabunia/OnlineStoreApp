package com.example.final_project.presentation.event.bottom_sheet_fragment.card

import com.example.final_project.presentation.model.wallet.Card

sealed class CardEvent {
    data class AddCard(val newCard: Card): CardEvent()
    object ResetErrorMessage: CardEvent()
}