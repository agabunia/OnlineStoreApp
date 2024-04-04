package com.example.final_project.presentation.model.common_product_list

data class Products(
    val products: List<ProductDetailed>
) {
    data class ProductDetailed(
        val id: Int,
        val title: String,
        val description: String,
        val price: Int,
        val thumbnail: String,
        var isAdded: Boolean = false
    )
}