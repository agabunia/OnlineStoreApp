package com.example.final_project.data.remote.repository.home

import com.example.final_project.data.common.HandleResponse
import com.example.final_project.data.common.Resource
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.service.home.CategoryListService
import com.example.final_project.domain.remote.repository.home.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryListRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val categoryListService: CategoryListService
) : CategoryListRepository {
    override suspend fun getCategoryList(): Flow<Resource<List<String>>> {
        return handleResponse.safeApiCall {
            categoryListService.getCategoryList()
        }.asResource {
            it
        }
    }
}