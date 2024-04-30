package com.example.final_project.presentation.state.wallet

import com.example.final_project.presentation.model.wallet.Card

data class WalletState(
    val allCards: List<Card>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
