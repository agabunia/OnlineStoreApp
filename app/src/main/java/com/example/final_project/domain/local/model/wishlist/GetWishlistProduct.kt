package com.example.final_project.domain.local.model.wishlist

data class GetWishlistProduct(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    var quantity: Int,
    var sumPrice: Int,
    val thumbnail: String
)
