package com.example.final_project.data.remote.mapper.home

import com.example.final_project.data.remote.mapper.common_product_list.toDomain
import com.example.final_project.data.remote.model.home.HomeMainModelDto
import com.example.final_project.domain.remote.model.home.GetHomeMainModel

fun HomeMainModelDto.toDomain(): GetHomeMainModel {
    return GetHomeMainModel(
        getBannerImage = bannerImageDto,
        getPromotionImages = promotionImagesDto,
        getCategoryProductsList = categoryProductsListDto.map { categoryProductDto ->
            GetHomeMainModel.GetCategoryProductModel(
                getCategory = categoryProductDto.categoryDto,
                getProductsList = categoryProductDto.productsListDto.toDomain()
            )
        }
    )
}