package com.example.final_project.data.remote.service.search

import com.example.final_project.data.remote.model.common_product_list.ProductsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductSearchService {
    @GET("https://dummyjson.com/products/search")
    suspend fun getProduct(
        @Query("q") search: String
    ): Response<ProductsDto>
}