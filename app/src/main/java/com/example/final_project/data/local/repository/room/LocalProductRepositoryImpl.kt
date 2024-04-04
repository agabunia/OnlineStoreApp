package com.example.final_project.data.local.repository.room

import com.example.final_project.data.local.dao.ProductDao
import com.example.final_project.data.local.mapper.toData
import com.example.final_project.data.local.mapper.toDomain
import com.example.final_project.domain.local.repository.wishlist.LocalProductRepository
import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : LocalProductRepository {
    override fun getAllProducts(): Flow<List<GetWishlistProduct>> {
        return productDao.getAllProducts().map { productList ->
            productList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insertProduct(product: GetWishlistProduct) {
        productDao.insertProduct(product = product.toData())
    }

    override suspend fun increaseProductQuantity(id: Int) {
        productDao.increaseProductQuantity(productId = id)
    }

    override suspend fun decreaseProductQuantity(id: Int) {
        productDao.decreaseProductQuantity(productId = id)
    }

    override fun sumOfAllProduct(): Flow<Int> {
        return productDao.sumOfAllProduct()
    }

    override suspend fun deleteProduct(product: GetWishlistProduct) {
        productDao.deleteProduct(product = product.toData())
    }

    override suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

}
