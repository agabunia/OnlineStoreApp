package com.example.final_project.presentation.event.login

sealed class LoginEvent {
    data class Login(val email: String, val password: String, val saveUser: Boolean) : LoginEvent()
    object Register : LoginEvent()
    object ResetErrorMessage : LoginEvent()
}
