package com.example.final_project.domain.remote.usecase.profile

import android.net.Uri
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.repository.firebase_cloud_store.image_upload.UploadUriRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileImageUseCase @Inject constructor(
    private val uploadUriRepository: UploadUriRepository
) {
    suspend operator fun invoke(uid: String): Flow<Resource<Uri>> {
        return uploadUriRepository.retrieveUploadedImage(uid = uid)
    }
}
