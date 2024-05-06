package com.example.final_project.presentation.screen.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.profile_image.ReadUserUidUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.InsertProductInLocalUseCase
import com.example.final_project.domain.remote.usecase.payment.PaymentUseCase
import com.example.final_project.domain.remote.usecase.product.GetProductDetailedUseCase
import com.example.final_project.domain.remote.usecase.wallet.GetAllCardsUseCase
import com.example.final_project.presentation.event.product.ProductEvent
import com.example.final_project.presentation.mapper.common_product_list.toDomain
import com.example.final_project.presentation.mapper.product.toDomain
import com.example.final_project.presentation.mapper.product.toPresenter
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.product.ProductDetailed
import com.example.final_project.presentation.screen.login.LoginViewModel
import com.example.final_project.presentation.screen.payment.PaymentViewModel
import com.example.final_project.presentation.state.product.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailedViewModel @Inject constructor(
    private val getProductDetailedUseCase: GetProductDetailedUseCase,
    private val insertProductInLocalUseCase: InsertProductInLocalUseCase,
    private val paymentUseCase: PaymentUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val readUserUidUseCase: ReadUserUidUseCase,
) : ViewModel() {

    private val _productState = MutableStateFlow(ProductState())
    val productState: SharedFlow<ProductState> = _productState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> get() = _uiEvent

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.FetchProductDetailed -> fetchProductDetailed(id = event.id)
            is ProductEvent.ResetErrorMessage -> errorMessage(message = null)
            is ProductEvent.SaveProduct -> saveProductInDatabase(product = event.product)
            is ProductEvent.BuyProduct -> buyProduct(amount = event.amount)
            is ProductEvent.NavigateBack -> navigateBack()
            is ProductEvent.IncreaseQuantity -> increaseQuantity(
                quantity = event.quantity,
                stock = event.stock
            )

            is ProductEvent.DecreaseQuantity -> decreaseQuantity(quantity = event.quantity)
        }
    }

    private fun fetchProductDetailed(id: Int) {
        viewModelScope.launch {
            getProductDetailedUseCase(id = id).collect {
                when (it) {
                    is Resource.Success -> {
                        _productState.update { currentState ->
                            currentState.copy(productDetails = it.data.toPresenter())
                        }
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _productState.update { currentState ->
                            currentState.copy(isLoading = it.loading)
                        }
                    }
                }
            }
        }
    }

    private fun saveProductInDatabase(product: ProductDetailed) {
        viewModelScope.launch {
            insertProductInLocalUseCase(product = product.toDomain())
        }
    }

    private fun buyProduct(amount: Int) {
        viewModelScope.launch {
            val uid = readUserUidUseCase().first()
            getAllCardsUseCase(uid).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data.isNotEmpty()) {
                            val isSuccessful = paymentUseCase(amount = amount)
                            navigateToPayment(isSuccessful = isSuccessful)
                        } else {
                            errorMessage(message = "Wallet is empty, add card")
                        }
                    }

                    is Resource.Error -> errorMessage(message = "Error with the wallet, try again")
                    is Resource.Loading -> _productState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }
                }
            }
        }
    }

    private fun navigateToPayment(isSuccessful: Boolean) {
        viewModelScope.launch {
            if (isSuccessful) {
                _uiEvent.emit(UIEvent.NavigateToPayment(true))
            } else {
                _uiEvent.emit(UIEvent.NavigateToPayment(false))
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.NavigateBack)
        }
    }

    private fun errorMessage(message: String?) {
        _productState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    private fun increaseQuantity(quantity: Int, stock: Int) {
        var newQuantity = quantity
        if (quantity < stock) {
            newQuantity += 1
        }
        _productState.update { currentState ->
            currentState.copy(quantity = newQuantity)
        }
    }

    private fun decreaseQuantity(quantity: Int) {
        var newQuantity = quantity
        if (quantity > 0) {
            newQuantity -= 1
        }
        _productState.update { currentState ->
            currentState.copy(quantity = newQuantity)
        }
    }

    sealed interface UIEvent {
        data class NavigateToPayment(val isSuccessful: Boolean) : UIEvent
        object NavigateBack : UIEvent
    }

}