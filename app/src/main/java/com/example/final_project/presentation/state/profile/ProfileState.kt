package com.example.final_project.presentation.state.profile

import android.net.Uri

data class ProfileState(
    val userImage: Uri? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
