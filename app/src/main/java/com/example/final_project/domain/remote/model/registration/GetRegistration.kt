package com.example.final_project.domain.remote.model.registration

import com.google.firebase.auth.FirebaseUser

data class GetRegistration(
    val user: FirebaseUser
)
