package com.example.final_project.presentation.state.search

import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.product.ProductDetailed

data class SearchState(
    val productsList: List<ProductCommonDetailed>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
