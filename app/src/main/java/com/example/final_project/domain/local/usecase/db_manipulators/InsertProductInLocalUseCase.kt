package com.example.final_project.domain.local.usecase.db_manipulators

import com.example.final_project.domain.local.repository.wishlist.LocalProductRepository
import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import javax.inject.Inject

class InsertProductInLocalUseCase @Inject constructor(
    private val localProductRepository: LocalProductRepository
) {
    suspend operator fun invoke(product: GetWishlistProduct) =
        localProductRepository.insertProduct(product = product)
}