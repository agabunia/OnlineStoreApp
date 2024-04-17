package com.example.final_project.domain.remote.usecase.validators.user

class PasswordValidatorUseCase {
    operator fun invoke(password: String): Boolean = password.isNotBlank()
}
