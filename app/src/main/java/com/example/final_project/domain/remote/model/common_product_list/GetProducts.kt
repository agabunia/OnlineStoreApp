package com.example.final_project.domain.remote.model.common_product_list

data class GetProducts(
    val products: List<GetProductDetailed>
) {
    data class GetProductDetailed(
        val id: Int,
        val title: String,
        val description: String,
        val price: Int,
        val thumbnail: String,
    )
}