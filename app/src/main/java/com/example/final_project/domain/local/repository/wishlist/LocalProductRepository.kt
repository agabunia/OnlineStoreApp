package com.example.final_project.domain.local.repository.wishlist

import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import kotlinx.coroutines.flow.Flow

interface LocalProductRepository {

    fun getAllProducts(): Flow<List<GetWishlistProduct>>

    suspend fun insertProduct(product: GetWishlistProduct)

    suspend fun increaseProductQuantity(id: Int)

    suspend fun decreaseProductQuantity(id: Int)

    fun sumOfAllProduct(): Flow<Int>

    suspend fun deleteProduct(product: GetWishlistProduct)

    suspend fun deleteAllProducts()

}
