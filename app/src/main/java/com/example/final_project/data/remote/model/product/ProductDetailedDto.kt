package com.example.final_project.data.remote.model.product

import com.squareup.moshi.Json

data class ProductDetailedDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "discountPercentage")
    val discountPercentage: Float,
    @Json(name = "rating")
    val rating: Float,
    @Json(name = "stock")
    val stock: Int,
    @Json(name = "brand")
    val brand: String,
    @Json(name = "category")
    val category: String,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "images")
    val images: List<String>
)
