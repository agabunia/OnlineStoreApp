package com.example.final_project.data.remote.mapper.common_product_list

import com.example.final_project.data.remote.model.common_product_list.ProductsDto
import com.example.final_project.domain.remote.model.common_product_list.GetProducts

fun ProductsDto.toDomain(): GetProducts {
    return GetProducts(products = products.map {
        it.toDomain()
    })
}