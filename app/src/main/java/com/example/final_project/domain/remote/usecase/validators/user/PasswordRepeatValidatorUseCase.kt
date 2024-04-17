package com.example.final_project.domain.remote.usecase.validators.user

class PasswordRepeatValidatorUseCase {
    operator fun invoke(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }
}
