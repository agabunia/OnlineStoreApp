package com.example.final_project.presentation.mapper.product

import com.example.final_project.domain.remote.model.product.GetProductDetailed
import com.example.final_project.presentation.model.product.ProductDetailed

fun GetProductDetailed.toPresenter(): ProductDetailed {
    return ProductDetailed(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        category = category,
        thumbnail = thumbnail,
        images = images
    )
}