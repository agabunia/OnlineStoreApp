package com.example.final_project.presentation.state.card

data class CardState(
    val isAdded: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)