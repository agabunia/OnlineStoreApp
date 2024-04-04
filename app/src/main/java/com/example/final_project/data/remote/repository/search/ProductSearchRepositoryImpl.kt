package com.example.final_project.data.remote.repository.search

import com.example.final_project.data.common.HandleResponse
import com.example.final_project.data.common.Resource
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.common_product_list.toDomain
import com.example.final_project.data.remote.service.search.ProductSearchService
import com.example.final_project.domain.remote.model.common_product_list.GetProducts
import com.example.final_project.domain.remote.repository.search.ProductSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductSearchRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val productSearchService: ProductSearchService
) : ProductSearchRepository {
    override suspend fun searchProduct(search: String): Flow<Resource<GetProducts>> {
        return handleResponse.safeApiCall {
            productSearchService.getProduct(search = search)
        }.asResource {
            it.toDomain()
        }
    }
}