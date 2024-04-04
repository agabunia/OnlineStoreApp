package com.example.final_project.data.remote.service.home

import retrofit2.Response
import retrofit2.http.GET

interface CategoryListService {
    @GET("https://dummyjson.com/products/categories")
    suspend fun getCategoryList(): Response<List<String>>
}