package com.example.final_project.data.common

sealed class Resource<out D : Any> {
    data class Success<out D : Any>(val data: D) : Resource<D>()
    data class Error<out D : Any>(val errorMessage: String) : Resource<D>()
    data class Loading<Nothing : Any>(val loading: Boolean) : Resource<Nothing>()
}
