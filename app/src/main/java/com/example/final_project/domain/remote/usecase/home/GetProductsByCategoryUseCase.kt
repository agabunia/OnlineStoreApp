package com.example.final_project.domain.remote.usecase.home

import com.example.final_project.domain.remote.repository.home.ProductByCategoryRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productByCategoryRepository: ProductByCategoryRepository
) {
    suspend operator fun invoke(category: String) =
        productByCategoryRepository.getProductByCategory(category = category)
}