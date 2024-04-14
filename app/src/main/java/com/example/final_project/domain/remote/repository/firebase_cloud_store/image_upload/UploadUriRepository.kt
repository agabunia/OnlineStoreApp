package com.example.final_project.domain.remote.repository.firebase_cloud_store.image_upload

import android.net.Uri
import com.example.final_project.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface UploadUriRepository {
    suspend fun uploadImage(uri: Uri, uid: String): Flow<Resource<Boolean>>
    suspend fun retrieveUploadedImage(uid: String): Flow<Resource<Uri>>
}
