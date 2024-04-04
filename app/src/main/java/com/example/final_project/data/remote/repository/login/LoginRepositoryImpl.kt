package com.example.final_project.data.remote.repository.login

import com.example.final_project.data.common.ErrorHandler
import com.example.final_project.data.common.Resource
import com.example.final_project.data.common.await
import com.example.final_project.data.remote.mapper.login.toDomain
import com.example.final_project.data.remote.model.login.LoginDto
import com.example.final_project.domain.remote.model.login.GetLogin
import com.example.final_project.domain.remote.repository.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<GetLogin>> {
        return flow {
            emit(Resource.Loading(loading = true))
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(LoginDto(user = result.user!!).toDomain()))
            } catch (e: FirebaseAuthException) {
                val errorMessage = when (e.errorCode) {
                    "ERROR_INVALID_CREDENTIAL" -> ErrorHandler.IncorrectCredentialFailure().message

                    else -> "Authentication failed: ${e.message}"
                }
                emit(Resource.Error(errorMessage = errorMessage))
            }
            emit(Resource.Loading(loading = false))
        }
    }
}