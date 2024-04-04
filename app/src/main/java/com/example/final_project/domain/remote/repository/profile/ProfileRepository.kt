package com.example.final_project.domain.remote.repository.profile

import android.net.Uri
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.model.login.GetLogin
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun changeImage(uri: Uri): Flow<Resource<GetLogin>>
}