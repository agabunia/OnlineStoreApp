package com.example.final_project.presentation.screen.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.local.usecase.datastore.language.ChangeLanguageDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.language.GetLanguageDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.theme.ChangeThemeDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.theme.GetThemeDataStoreUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.DecreaseLocalProductQuantityUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.DeleteAllLocalProductsUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.DeleteLocalProductUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.GetAllLocalProductsUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.GetSumOfAllLocalProductsUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.IncreaseLocalProductQuantityUseCase
import com.example.final_project.domain.remote.usecase.payment.PaymentUseCase
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.event.wishlist.WishlistEvent
import com.example.final_project.presentation.mapper.wishlist.toDomain
import com.example.final_project.presentation.mapper.wishlist.toPresenter
import com.example.final_project.presentation.model.wishlist.WishlistProduct
import com.example.final_project.presentation.screen.product.ProductDetailedViewModel
import com.example.final_project.presentation.state.app_state.AppState
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
    private val paymentUseCase: PaymentUseCase,
    private val changeThemeDataStoreUseCase: ChangeThemeDataStoreUseCase,
    private val getThemeDataStoreUseCase: GetThemeDataStoreUseCase,
    private val changeLanguageDataStoreUseCase: ChangeLanguageDataStoreUseCase,
    private val getLanguageDataStoreUseCase: GetLanguageDataStoreUseCase,
) : ViewModel() {

    private val _wishlistState = MutableStateFlow(WishlistState())
    val wishlistState: SharedFlow<WishlistState> = _wishlistState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> get() = _uiEvent

    private val _appState = MutableStateFlow(AppState())
    val appState: SharedFlow<AppState> = _appState.asStateFlow()

    fun onEvent(event: WishlistEvent) {
        when (event) {
            is WishlistEvent.FetchAllProducts -> fetchAllProducts()
            is WishlistEvent.DeleteAllItem -> deleteAllProducts()
            is WishlistEvent.DeleteItem -> deleteProduct(product = event.product)
            is WishlistEvent.IncreaseItemQuantity -> increaseQuantity(id = event.id)
            is WishlistEvent.DecreaseItemQuantity -> decreaseQuantity(id = event.id)
            is WishlistEvent.ResetErrorMessage -> errorMessage(message = null)
            is WishlistEvent.BuyProduct -> buyProduct(amount = event.amount)
            is WishlistEvent.ChangeTheme -> setLightTheme(isLight = event.isLight)
            is WishlistEvent.ChangeLanguage -> changeLanguage(isGeorgian = event.isGeorgian)
        }
    }

    init {
        getTotalPrice()
        getTheme()
        getLanguage()
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

    private fun setLightTheme(isLight: Boolean) {
        viewModelScope.launch {
            changeThemeDataStoreUseCase(isLight = isLight)
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            getThemeDataStoreUseCase().collect {
                if (it == "dark") {
                    _appState.update { themeState ->
                        themeState.copy(isLight = false)
                    }
                } else {
                    _appState.update { themeState ->
                        themeState.copy(isLight = true)
                    }
                }
            }
        }
    }

    private fun changeLanguage(isGeorgian: Boolean) {
        viewModelScope.launch {
            changeLanguageDataStoreUseCase(isGeorgian = isGeorgian)
        }
    }

    private fun getLanguage() {
        viewModelScope.launch {
            getLanguageDataStoreUseCase().collect {
                if (it == "ka") {
                    _appState.update { languageState ->
                        languageState.copy(isGeorgian = true)
                    }
                } else {
                    _appState.update { languageState ->
                        languageState.copy(isGeorgian = false)
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

    sealed class UIEvent() {
        data class NavigateToPayment(val isSuccessful: Boolean) : UIEvent()
    }

}