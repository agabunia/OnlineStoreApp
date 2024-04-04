package com.example.final_project.data.remote.repository.home

import com.example.final_project.data.common.HandleResponse
import com.example.final_project.data.common.Resource
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.home.toDomain
import com.example.final_project.data.remote.service.home.HomeModelService
import com.example.final_project.domain.remote.model.home.GetHomeMainModel
import com.example.final_project.domain.remote.repository.home.HomeDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeDataRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val homeModelService: HomeModelService
) : HomeDataRepository {
    override suspend fun getHomeData(): Flow<Resource<GetHomeMainModel>> {
        return handleResponse.safeApiCall {
            homeModelService.getHomeData()
        }.asResource { dto ->
            dto.toDomain()
        }
    }
}