package com.example.final_project.data.remote.service.product

import com.example.final_project.data.remote.model.product.ProductDetailedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDetailedService {
    @GET("https://dummyjson.com/products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Response<ProductDetailedDto>
}