package com.example.final_project.data.remote.service.search

import com.example.final_project.data.remote.model.common_product_list.ProductsDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("https://dummyjson.com/products")
    suspend fun getProduct(): Response<ProductsDto>
}