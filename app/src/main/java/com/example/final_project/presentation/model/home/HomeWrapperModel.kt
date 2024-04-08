package com.example.final_project.presentation.model.home

sealed class HomeWrapperModel {
    data class BannerImage(val bannerImage: String): HomeWrapperModel()
    data class PromotionImages(val promotionImages: List<String>): HomeWrapperModel()
    data class CategoryWrapperList(val categoryWrapperList: List<HomeMainModel.CategoryProductModel>): HomeWrapperModel()
}
