package com.example.final_project.presentation.screen.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.local.usecase.db_manipulators.DecreaseLocalProductQuantityUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.DeleteAllLocalProductsUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.DeleteLocalProductUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.GetAllLocalProductsUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.GetSumOfAllLocalProductsUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.IncreaseLocalProductQuantityUseCase
import com.example.final_project.domain.remote.usecase.payment.PaymentUseCase
import com.example.final_project.presentation.event.wishlist.WishlistEvent
import com.example.final_project.presentation.mapper.wishlist.toDomain
import com.example.final_project.presentation.mapper.wishlist.toPresenter
import com.example.final_project.presentation.model.wishlist.WishlistProduct
import com.example.final_project.presentation.screen.product.ProductDetailedViewModel
import com.example.final_project.presentation.state.wishlist.WishlistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getAllLocalProductsUseCase: GetAllLocalProductsUseCase,
    private val deleteAllLocalProductsUseCase: DeleteAllLocalProductsUseCase,
    private val deleteLocalProductUseCase: DeleteLocalProductUseCase,
    private val increaseLocalProductQuantityUseCase: IncreaseLocalProductQuantityUseCase,
    private val decreaseLocalProductQuantityUseCase: DecreaseLocalProductQuantityUseCase,
    private val getSumOfAllLocalProductsUseCase: GetSumOfAllLocalProductsUseCase,
    private val paymentUseCase: PaymentUseCase
) : ViewModel() {

    private val _wishlistState = MutableStateFlow(WishlistState())
    val wishlistState: SharedFlow<WishlistState> = _wishlistState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> get() = _uiEvent

    fun onEvent(event: WishlistEvent) {
        when (event) {
            is WishlistEvent.FetchAllProducts -> fetchAllProducts()
            is WishlistEvent.DeleteAllItem -> deleteAllProducts()
            is WishlistEvent.DeleteItem -> deleteProduct(product = event.product)
            is WishlistEvent.IncreaseItemQuantity -> increaseQuantity(id = event.id)
            is WishlistEvent.DecreaseItemQuantity -> decreaseQuantity(id = event.id)
            is WishlistEvent.ResetErrorMessage -> errorMessage(message = null)
            is WishlistEvent.BuyProduct -> buyProduct(amount = event.amount)
        }
    }

    init {
        getTotalPrice()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            getAllLocalProductsUseCase().collect { list ->
                _wishlistState.update { currentState ->
                    currentState.copy(productsList = list.map {
                        it.toPresenter()
                    })
                }
            }
        }
    }

    private fun getTotalPrice() {
        viewModelScope.launch {
            getSumOfAllLocalProductsUseCase().collect {
                _wishlistState.update { currentState ->
                    currentState.copy(productsTotalSum = it)
                }
            }
        }
    }

    private fun deleteAllProducts() {
        viewModelScope.launch {
            deleteAllLocalProductsUseCase()
        }
    }

    private fun deleteProduct(product: WishlistProduct) {
        viewModelScope.launch {
            deleteLocalProductUseCase(product = product.toDomain())
        }
    }

    private fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            increaseLocalProductQuantityUseCase(id = id)
        }
    }

    private fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            decreaseLocalProductQuantityUseCase(id = id)
        }
    }

    private fun errorMessage(message: String?) {
        _wishlistState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    private fun buyProduct(amount: Int) {
        val isSuccessful = paymentUseCase(amount = amount)
        navigateToPayment(isSuccessful = isSuccessful)
    }

    private fun navigateToPayment(isSuccessful: Boolean) {
        viewModelScope.launch {
            if (isSuccessful) {
                _uiEvent.emit(UIEvent.navigateToPayment(true))
            } else {
                _uiEvent.emit(UIEvent.navigateToPayment(false))
            }
        }
    }

    sealed class UIEvent() {
        data class navigateToPayment(val isSuccessful: Boolean) : UIEvent()
    }

}