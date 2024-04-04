package com.example.final_project.presentation.state.registration

data class RegistrationState(
    val data: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
