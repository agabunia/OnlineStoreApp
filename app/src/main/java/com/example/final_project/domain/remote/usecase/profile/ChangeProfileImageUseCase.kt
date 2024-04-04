package com.example.final_project.domain.remote.usecase.profile

import android.net.Uri
import com.example.final_project.domain.remote.repository.profile.ProfileRepository
import javax.inject.Inject

class ChangeProfileImageUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(uri: Uri) = profileRepository.changeImage(uri = uri)
}