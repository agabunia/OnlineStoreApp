package com.example.final_project.presentation.event.profile

import android.net.Uri

sealed class ProfileEvent {
    object LogOut: ProfileEvent()
    object NavigateToTerms: ProfileEvent()
    data class UploadImage(val uri: Uri): ProfileEvent()
    object GetProfileImage: ProfileEvent()
    object NavigateToWallet: ProfileEvent()
}