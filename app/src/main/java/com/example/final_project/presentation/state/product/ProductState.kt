package com.example.final_project.presentation.state.product

import com.example.final_project.presentation.model.product.ProductDetailed

data class ProductState(
    val productDetails: ProductDetailed? = null,
    val errorMessage: String? = null,
    val errorMessageId: Int? = null,
    val quantity: Int? = null,
    val isLoading: Boolean = false
)
