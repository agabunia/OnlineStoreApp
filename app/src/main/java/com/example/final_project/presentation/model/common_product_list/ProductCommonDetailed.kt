package com.example.final_project.presentation.model.common_product_list

data class ProductCommonDetailed(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
    var isAdded: Boolean = false
)