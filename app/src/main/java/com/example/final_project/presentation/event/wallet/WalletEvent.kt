package com.example.final_project.presentation.event.wallet

sealed class WalletEvent {
    object GetAllCards : WalletEvent()
    object NavigateToProfile : WalletEvent()
    object OpenBottomSheetFragment : WalletEvent()
    object ResetErrorMessage: WalletEvent()
    data class DeleteCard(var id: String): WalletEvent()
}