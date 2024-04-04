package com.example.final_project.presentation.state.login

data class LoginState(
    val data: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
