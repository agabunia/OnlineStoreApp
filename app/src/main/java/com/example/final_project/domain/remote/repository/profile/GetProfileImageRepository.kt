package com.example.final_project.domain.remote.repository.profile

import android.net.Uri
import com.example.final_project.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface GetProfileImageRepository {
    suspend fun getImage(): Flow<Resource<Uri>>
}