package com.example.final_project.data.remote.repository.firebase_cloud_store.image_upload

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.final_project.data.common.Resource
import com.example.final_project.data.common.await
import com.example.final_project.domain.remote.repository.firebase_cloud_store.image_upload.UploadUriRepository
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class UploadUriRepositoryImpl @Inject constructor(
    private val storageReference: StorageReference,
    private val context: Context
) : UploadUriRepository {

    private suspend fun getBitmapFromUri(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        }
    }

    override suspend fun uploadImage(uri: Uri, uid: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                val bitmap = getBitmapFromUri(uri = uri)
                val fileReference = storageReference.child("images/$uid/profile_picture.jpg")
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val data = byteArrayOutputStream.toByteArray()

                val uploadTask = fileReference.putBytes(data)
                uploadTask.await()

                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.message.toString()))
            }
        }
    }

    override suspend fun retrieveUploadedImage(uid: String): Flow<Resource<Uri>> {
        return flow {
            try {
                val fileReference = storageReference.child("images/$uid/profile_picture.jpg")
                val downloadUri = fileReference.downloadUrl.await()

                emit(Resource.Success(downloadUri))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.message.toString()))
            }
        }
    }
}