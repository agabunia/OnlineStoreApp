package com.example.final_project.presentation.event.home

import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.product.ProductDetailed

sealed class HomeEvent {
    object FetchProducts : HomeEvent()
    object ResetErrorMessage : HomeEvent()
    data class ChangeTheme(val isLight: Boolean) : HomeEvent()
    data class ChangeLanguage(val isGeorgian: Boolean) : HomeEvent()
    data class SaveProduct(var product: ProductCommonDetailed): HomeEvent()
    data class MoveToDetailed(val id: Int): HomeEvent()
}