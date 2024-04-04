package com.example.final_project.domain.remote.usecase.login

import com.example.final_project.domain.remote.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String) =
        loginRepository.login(email = email, password = password)
}
