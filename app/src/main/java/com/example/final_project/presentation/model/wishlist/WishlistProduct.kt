package com.example.final_project.presentation.model.wishlist

data class WishlistProduct(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    var quantity: Int,
    var sumPrice: Int,
    val thumbnail: String
)
