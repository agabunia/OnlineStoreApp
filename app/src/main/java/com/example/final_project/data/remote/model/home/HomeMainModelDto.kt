package com.example.final_project.data.remote.model.home

import com.example.final_project.data.remote.model.common_product_list.ProductsDto
import com.squareup.moshi.Json

data class HomeMainModelDto(
    @Json(name = "banner_image")
    val bannerImageDto: String,
    @Json(name = "promotion_images")
    val promotionImagesDto: List<String>,
    @Json(name = "category_wrapper_list")
    val categoryProductsListDto: List<CategoryProductModelDto>
) {
    data class CategoryProductModelDto(
        @Json(name = "category")
        val categoryDto: String,
        @Json(name = "products")
        val productsListDto: ProductsDto
    )
}