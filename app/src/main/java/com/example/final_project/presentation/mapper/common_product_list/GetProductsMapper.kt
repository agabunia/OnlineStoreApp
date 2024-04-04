package com.example.final_project.presentation.mapper.common_product_list

import com.example.final_project.domain.remote.model.common_product_list.GetProducts
import com.example.final_project.presentation.model.common_product_list.Products

fun GetProducts.toPresenter(): Products {
    val products = products.map {
        Products.ProductDetailed(
            id = it.id,
            title = it.title,
            price = it.price,
            thumbnail = it.thumbnail,
            description = it.description
        )
    }
    return Products(products = products)
}