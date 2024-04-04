package com.example.final_project.data.common

sealed class ErrorHandler {
    data class UserAlreadyExistsFailure(val message: String = "The User Already Exists") :
        ErrorHandler()

    data class IncorrectCredentialFailure(val message: String = "Incorrect Email/Password") :
        ErrorHandler()

    data class ServerFailure(val message: String = "Server Connection Failed") : ErrorHandler()
    data class NoInternetFailure(val message: String = "No Internet Connection") : ErrorHandler()
}
