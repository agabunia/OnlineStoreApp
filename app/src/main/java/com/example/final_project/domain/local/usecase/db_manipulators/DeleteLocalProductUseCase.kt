package com.example.final_project.domain.local.usecase.db_manipulators

import com.example.final_project.domain.local.repository.wishlist.LocalProductRepository
import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import javax.inject.Inject

class DeleteLocalProductUseCase @Inject constructor(
    private val localProductRepository: LocalProductRepository
) {
    suspend operator fun invoke(product: GetWishlistProduct) =
        localProductRepository.deleteProduct(product = product)
}