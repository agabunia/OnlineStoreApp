package com.example.final_project.presentation.mapper.common_product_list

import com.example.final_project.domain.local.model.wishlist.GetWishlistProduct
import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.wishlist.WishlistProduct

fun ProductCommonDetailed.toDomain(): GetWishlistProduct {
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