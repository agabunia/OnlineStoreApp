package com.example.final_project.data.remote.mapper.common_product_list

import com.example.final_project.data.remote.model.common_product_list.ProductsDto
import com.example.final_project.domain.remote.model.common_product_list.GetProducts

fun ProductsDto.toDomain(): GetProducts {
    val getProducts = products.map {
        GetProducts.GetProductDetailed(
            id = it.id,
            title = it.title,
            price = it.price,
            thumbnail = it.thumbnail,
            description = it.description
        )
    }
    return GetProducts(products = getProducts)
}