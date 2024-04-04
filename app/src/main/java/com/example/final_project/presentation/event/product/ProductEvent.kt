package com.example.final_project.presentation.event.product

import com.example.final_project.presentation.model.product.ProductDetailed

sealed class ProductEvent {
    data class FetchProductDetailed(val id: Int): ProductEvent()
    object ResetErrorMessage: ProductEvent()
    data class SaveProduct(var product: ProductDetailed): ProductEvent()
    data class BuyProduct(var amount: Int): ProductEvent()
    object NavigateBack: ProductEvent()
    data class IncreaseQuantity(val quantity: Int, val stock: Int): ProductEvent()
    data class DecreaseQuantity(val quantity: Int): ProductEvent()
}