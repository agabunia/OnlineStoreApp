package com.example.final_project.domain.remote.repository.home

import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.model.common_product_list.GetProducts
import kotlinx.coroutines.flow.Flow

interface ProductByCategoryRepository {
    suspend fun getProductByCategory(category: String): Flow<Resource<GetProducts>>
}