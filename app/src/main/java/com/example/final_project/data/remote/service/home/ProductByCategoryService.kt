package com.example.final_project.data.remote.service.home

import com.example.final_project.data.remote.model.common_product_list.ProductsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductByCategoryService {
    @GET("https://dummyjson.com/products/category/{category}?limit=5")
    suspend fun getProduct(
        @Path("category") category: String
    ): Response<ProductsDto>
}