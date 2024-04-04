package com.example.final_project.domain.remote.repository.home

import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.model.home.GetHomeMainModel
import kotlinx.coroutines.flow.Flow

interface HomeDataRepository {
    suspend fun getHomeData(): Flow<Resource<GetHomeMainModel>>
}