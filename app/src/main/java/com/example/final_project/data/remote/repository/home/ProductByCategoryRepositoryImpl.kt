package com.example.final_project.data.remote.repository.home

import com.example.final_project.data.common.HandleResponse
import com.example.final_project.data.common.Resource
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.common_product_list.toDomain
import com.example.final_project.data.remote.service.home.ProductByCategoryService
import com.example.final_project.domain.remote.model.common_product_list.GetProducts
import com.example.final_project.domain.remote.repository.home.ProductByCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductByCategoryRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val productByCategoryService: ProductByCategoryService
) : ProductByCategoryRepository {
    override suspend fun getProductByCategory(category: String): Flow<Resource<GetProducts>> {
        return handleResponse.safeApiCall {
            productByCategoryService.getProduct(category = category)
        }.asResource {
            it.toDomain()
        }
    }
}