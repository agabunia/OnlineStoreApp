package com.example.final_project.presentation.event.profile

import android.net.Uri

sealed class ProfileEvent {
    object LogOut: ProfileEvent()
    object Terms: ProfileEvent()
//    object GetUserProfileImage: ProfileEvent()
    data class ChangeImage(val uri: Uri): ProfileEvent()
}