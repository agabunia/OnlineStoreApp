package com.example.final_project.domain.remote.repository.home

import com.example.final_project.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryListRepository {
    suspend fun getCategoryList(): Flow<Resource<List<String>>>
}