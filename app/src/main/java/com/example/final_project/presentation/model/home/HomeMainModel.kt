package com.example.final_project.presentation.model.home

import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed

data class HomeMainModel(
    val bannerImage: String,
    val promotionImages: List<String>,
    val categoryWrapperList: List<CategoryProductModel>
) {
    data class CategoryProductModel(
        val categoryName: String,
        val productList: List<ProductCommonDetailed>
    )
}
