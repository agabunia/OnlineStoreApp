package com.example.final_project.presentation.event.wishlist

import com.example.final_project.presentation.model.wishlist.WishlistProduct

sealed class WishlistEvent {
    object FetchAllProducts : WishlistEvent()
    object ResetErrorMessage : WishlistEvent()
    data class DeleteItem(val product: WishlistProduct) : WishlistEvent()
    data class IncreaseItemQuantity(val id: Int) : WishlistEvent()
    data class DecreaseItemQuantity(val id: Int) : WishlistEvent()
    object DeleteAllItem : WishlistEvent()
    data class BuyProduct(var amount: Int): WishlistEvent()
    data class ChangeTheme(val isLight: Boolean) : WishlistEvent()
    data class ChangeLanguage(val isGeorgian: Boolean) : WishlistEvent()
}