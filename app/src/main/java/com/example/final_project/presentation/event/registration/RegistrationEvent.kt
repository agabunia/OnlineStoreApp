package com.example.final_project.presentation.event.registration

sealed class RegistrationEvent {
    data class Register(val email: String, val password: String, val passwordCheck: String) :
        RegistrationEvent()
    object Login: RegistrationEvent()

    object ResetErrorMessage : RegistrationEvent()
}
