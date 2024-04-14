package com.example.final_project.presentation.event.wallet

sealed class WalletEvent {
    object NavigateToProfile : WalletEvent()
    object OpenBottomSheetFragment : WalletEvent()
}