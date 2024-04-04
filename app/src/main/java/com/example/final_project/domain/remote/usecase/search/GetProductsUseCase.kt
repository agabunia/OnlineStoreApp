package com.example.final_project.domain.remote.usecase.search

import com.example.final_project.domain.remote.repository.search.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() = productRepository.getProducts()
}