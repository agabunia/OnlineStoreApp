package com.example.final_project.data.remote.mapper.common_product_list

import com.example.final_project.data.remote.model.common_product_list.ProductCommonDetailedDto
import com.example.final_project.domain.remote.model.common_product_list.GetProductCommonDetailed

fun ProductCommonDetailedDto.toDomain(): GetProductCommonDetailed {
    return GetProductCommonDetailed(
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail
    )
}