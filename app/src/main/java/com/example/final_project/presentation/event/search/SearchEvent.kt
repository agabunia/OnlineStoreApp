package com.example.final_project.presentation.event.search

import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed

sealed class SearchEvent {
    object FetchAllProducts: SearchEvent()
    data class FetchSearchProducts(val search: String): SearchEvent()
    data class MoveToDetailed(val id: Int): SearchEvent()
    object ResetErrorMessage: SearchEvent()
    data class SaveProduct(var product: ProductCommonDetailed): SearchEvent()
    data class ChangeTheme(val isLight: Boolean) : SearchEvent()
    data class ChangeLanguage(val isGeorgian: Boolean) : SearchEvent()
}