package com.example.final_project.domain.remote.repository.search

import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.model.common_product_list.GetProducts
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<GetProducts>>
}