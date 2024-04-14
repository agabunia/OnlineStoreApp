package com.example.final_project.domain.remote.usecase.profile

import android.net.Uri
import com.example.final_project.domain.remote.repository.firebase.image_upload.UploadUriRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val uploadUriRepository: UploadUriRepository
) {
    suspend operator fun invoke(uri: Uri, uid: String) = uploadUriRepository.uploadImage(uri = uri, uid = uid)
}