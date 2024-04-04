package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.final_project.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM productentity")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("UPDATE productentity SET quantity = quantity + 1, sum_price = sum_price + price WHERE id = :productId")
    suspend fun increaseProductQuantity(productId: Int)

    @Query("UPDATE productentity SET quantity = quantity - 1, sum_price = sum_price - price WHERE id = :productId AND quantity > 0")
    suspend fun decreaseProductQuantity(productId: Int)

    @Query("SELECT SUM(sum_price) FROM productentity")
    fun sumOfAllProduct(): Flow<Int>

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE FROM productentity")
    suspend fun deleteAllProducts()

}
