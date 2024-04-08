package com.example.final_project.domain.remote.model.common_product_list

data class GetProductCommonDetailed(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
)
