package com.example.final_project.data.remote.service.home

import com.example.final_project.data.remote.model.home.HomeMainModelDto
import retrofit2.Response
import retrofit2.http.GET

interface HomeModelService {
    @GET("https://run.mocky.io/v3/ffa5da3a-4033-47b7-b4a7-089d5f3b5ab2")
    suspend fun getHomeData(): Response<HomeMainModelDto>
}