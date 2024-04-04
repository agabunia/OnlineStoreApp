package com.example.final_project.presentation.mapper.home

import com.example.final_project.domain.remote.model.home.GetHomeMainModel
import com.example.final_project.presentation.mapper.common_product_list.toPresenter
import com.example.final_project.presentation.model.home.HomeMainModel

fun GetHomeMainModel.toPresenter(): HomeMainModel {
    return HomeMainModel(
        bannerImage = getBannerImage,
        promotionImages = getPromotionImages,
        categoryWrapperList = getCategoryProductsList.map { categoryProductsList ->
            HomeMainModel.CategoryProductModel(
                categoryName = categoryProductsList.getCategory,
                productList = categoryProductsList.getProductsList.toPresenter()
            )
        }
    )
}