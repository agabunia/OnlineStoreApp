package com.example.final_project.data.remote.model.common_product_list

import com.squareup.moshi.Json

data class ProductsDto(
    @Json(name = "products")
    val products: List<ProductCommonDetailedDto>
)
