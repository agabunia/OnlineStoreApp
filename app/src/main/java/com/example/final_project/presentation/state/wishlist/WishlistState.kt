package com.example.final_project.presentation.state.wishlist

import com.example.final_project.presentation.model.wishlist.WishlistProduct

data class WishlistState(
    val productsList: List<WishlistProduct>? = null,
    val productsTotalSum: Int? = null,
    val errorMessage: String? = null,
    val errorMessageId: Int? = null,
    val isLoading: Boolean = false
)
