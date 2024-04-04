package com.example.final_project.data.remote.repository.product

import com.example.final_project.data.common.HandleResponse
import com.example.final_project.data.common.Resource
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.product.toDomain
import com.example.final_project.data.remote.service.product.ProductDetailedService
import com.example.final_project.domain.remote.model.product.GetProductDetailed
import com.example.final_project.domain.remote.repository.product.ProductDetailedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDetailedRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val productDetailedService: ProductDetailedService
) : ProductDetailedRepository {
    override suspend fun getProductDetailed(id: Int): Flow<Resource<GetProductDetailed>> {
        return handleResponse.safeApiCall {
            productDetailedService.getProduct(id = id)
        }.asResource {
            it.toDomain()
        }
    }
}