package com.example.final_project.data.remote.repository.profile

import android.net.Uri
import com.example.final_project.data.common.ErrorHandler
import com.example.final_project.data.common.Resource
import com.example.final_project.data.common.await
import com.example.final_project.domain.remote.model.login.GetLogin
import com.example.final_project.domain.remote.repository.profile.ProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileChangeImageRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ProfileRepository {
    override suspend fun changeImage(uri: Uri): Flow<Resource<GetLogin>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val currentUser = firebaseAuth.currentUser
                currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setPhotoUri(uri).build()
                )?.await()

                val updatedUser = firebaseAuth.currentUser
                updatedUser?.let {
                    emit(Resource.Success(GetLogin(user = it)))
                } ?: emit(Resource.Error("Failed to retrieve updated user after profile change"))
            } catch (e: FirebaseAuthException) {
                val errorMessage = when (e.errorCode) {
                    "ERROR_INVALID_CREDENTIAL" -> ErrorHandler.IncorrectCredentialFailure().message
                    else -> "Authentication failed: ${e.message}"
                }
                emit(Resource.Error(errorMessage))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }
}