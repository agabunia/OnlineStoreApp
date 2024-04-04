package com.example.final_project.presentation.state.wishlist

import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.wishlist.WishlistProduct

data class WishlistState(
    val productsList: List<WishlistProduct>? = null,
    val productsTotalSum: Int? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
