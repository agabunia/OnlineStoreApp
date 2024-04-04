package com.example.final_project.domain.remote.usecase.profile

import com.example.final_project.domain.remote.repository.login.LoginRepository
import com.example.final_project.domain.remote.repository.profile.GetProfileImageRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetUserProfileImageUseCase @Inject constructor(
    private val repository: GetProfileImageRepository
) {
    suspend operator fun invoke() = repository.getImage()
}