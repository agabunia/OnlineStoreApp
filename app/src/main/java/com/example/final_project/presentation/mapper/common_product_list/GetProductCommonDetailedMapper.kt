package com.example.final_project.presentation.mapper.common_product_list

import com.example.final_project.domain.remote.model.common_product_list.GetProductCommonDetailed
import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed

fun GetProductCommonDetailed.toPresenter(): ProductCommonDetailed {
    return ProductCommonDetailed(
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail
    )
}