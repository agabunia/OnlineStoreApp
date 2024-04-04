package com.example.final_project.data.remote.repository.registration

import android.util.Log.d
import com.example.final_project.data.common.ErrorHandler
import com.example.final_project.data.common.Resource
import com.example.final_project.data.remote.mapper.registration.toDomain
import com.example.final_project.data.remote.model.registration.RegistrationDto
import com.example.final_project.domain.remote.model.registration.GetRegistration
import com.example.final_project.domain.remote.repository.registration.RegistrationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : RegistrationRepository {
    override suspend fun registration(
        email: String,
        password: String
    ): Flow<Resource<GetRegistration>> {
        return flow {
            emit(Resource.Loading(loading = true))
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                emit(Resource.Success(RegistrationDto(user = result.user!!).toDomain()))
            } catch (e: FirebaseAuthException) {
                d("ErrorMessageCodes", e.errorCode)
                val errorMessage = when (e.errorCode) {
                    "ERROR_EMAIL_ALREADY_IN_USE" -> ErrorHandler.UserAlreadyExistsFailure().message

                    else -> "Registration failed: ${e.message}"
                }
                emit(Resource.Error(errorMessage = errorMessage))
            }
            emit(Resource.Loading(loading = false))
        }
    }
}
