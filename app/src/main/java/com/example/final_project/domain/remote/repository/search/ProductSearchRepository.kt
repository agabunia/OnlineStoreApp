package com.example.final_project.domain.remote.repository.search

import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.model.common_product_list.GetProducts
import kotlinx.coroutines.flow.Flow

interface ProductSearchRepository {
    suspend fun searchProduct(search: String): Flow<Resource<GetProducts>>
}