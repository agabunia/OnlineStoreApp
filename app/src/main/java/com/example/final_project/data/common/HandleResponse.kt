package com.example.final_project.data.common

import android.util.Log.d
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.Response

class HandleResponse {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                d("ErrorMessageCodes", "${response.code()}")
                when (response.code()) {
                    500 -> emit(Resource.Error(errorMessage = ErrorHandler.ServerFailure().message))
                    502 -> emit(Resource.Error(errorMessage = ErrorHandler.NoInternetFailure().message))
                    else -> emit(
                        Resource.Error(
                            errorMessage = response.errorBody()?.string() ?: ""
                        )
                    )
                }
            }
        } catch (e: Throwable) {
            when (e) {
                is IOException -> emit(Resource.Error(ErrorHandler.NoInternetFailure().message))
                else -> emit(Resource.Error(errorMessage = e.message ?: ""))
            }
        }
        emit(Resource.Loading(loading = false))
    }
}