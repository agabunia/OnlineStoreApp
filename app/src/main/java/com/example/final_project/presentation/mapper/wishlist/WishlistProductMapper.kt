package com.example.final_project.presentation.mapper.wishlist

import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import com.example.final_project.presentation.model.wishlist.WishlistProduct

fun WishlistProduct.toDomain(): GetWishlistProduct {
    return GetWishlistProduct(
        id = id,
        title = title,
        description = description,
        price = price,
        quantity = quantity,
        sumPrice = sumPrice,
        thumbnail = thumbnail
    )
}