package com.example.final_project.domain.remote.usecase.product

import com.example.final_project.domain.remote.repository.product.ProductDetailedRepository
import javax.inject.Inject

class GetProductDetailedUseCase @Inject constructor(
    private val repository: ProductDetailedRepository
) {
    suspend operator fun invoke(id: Int) = repository.getProductDetailed(id = id)
}