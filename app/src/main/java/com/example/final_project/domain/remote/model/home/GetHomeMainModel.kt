package com.example.final_project.domain.remote.model.home

import com.example.final_project.domain.remote.model.common_product_list.GetProducts

data class GetHomeMainModel(
    val getBannerImage: String,
    val getPromotionImages: List<String>,
    val getCategoryProductsList: List<GetCategoryProductModel>
) {
    data class GetCategoryProductModel(
        val getCategory: String,
        val getProductsList: GetProducts
    )
}
