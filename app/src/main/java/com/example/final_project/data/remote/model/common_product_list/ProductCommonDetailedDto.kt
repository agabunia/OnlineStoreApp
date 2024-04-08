package com.example.final_project.data.remote.model.common_product_list

import com.squareup.moshi.Json

data class ProductCommonDetailedDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "thumbnail")
    val thumbnail: String,
)
