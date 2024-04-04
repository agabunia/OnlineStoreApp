package com.example.final_project.presentation.mapper.product

import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import com.example.final_project.presentation.model.product.ProductDetailed

fun ProductDetailed.toDomain(): GetWishlistProduct {
    return GetWishlistProduct(
        id = id,
        title = title,
        description = description,
        price = price,
        quantity = 1,
        sumPrice = price,
        thumbnail = thumbnail
    )
}