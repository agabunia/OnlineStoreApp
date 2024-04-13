package com.example.final_project.presentation.event.profile

import android.net.Uri

sealed class ProfileEvent {
    object LogOut: ProfileEvent()
    object Terms: ProfileEvent()
    data class UploadImage(val uri: Uri): ProfileEvent()
    object GetProfileImage: ProfileEvent()
}