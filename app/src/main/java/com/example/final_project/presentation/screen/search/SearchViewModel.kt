package com.example.final_project.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.language.ChangeLanguageDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.language.GetLanguageDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.theme.ChangeThemeDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.theme.GetThemeDataStoreUseCase
import com.example.final_project.domain.local.usecase.db_manipulators.InsertProductInLocalUseCase
import com.example.final_project.domain.remote.usecase.search.GetProductSearchUseCase
import com.example.final_project.domain.remote.usecase.search.GetProductsUseCase
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.event.search.SearchEvent
import com.example.final_project.presentation.mapper.common_product_list.toDomain
import com.example.final_project.presentation.mapper.common_product_list.toPresenter
import com.example.final_project.presentation.mapper.product.toDomain
import com.example.final_project.presentation.model.common_product_list.ProductCommonDetailed
import com.example.final_project.presentation.model.common_product_list.Products
import com.example.final_project.presentation.model.product.ProductDetailed
import com.example.final_project.presentation.state.app_state.AppState
import com.example.final_project.presentation.state.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductSearchUseCase: GetProductSearchUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val insertProductInLocalUseCase: InsertProductInLocalUseCase,
    private val changeThemeDataStoreUseCase: ChangeThemeDataStoreUseCase,
    private val getThemeDataStoreUseCase: GetThemeDataStoreUseCase,
    private val changeLanguageDataStoreUseCase: ChangeLanguageDataStoreUseCase,
    private val getLanguageDataStoreUseCase: GetLanguageDataStoreUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: SharedFlow<SearchState> = _searchState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SearchUIEvent>()
    val uiEvent: SharedFlow<SearchUIEvent> get() = _uiEvent

    private val _appState = MutableStateFlow(AppState())
    val appState: SharedFlow<AppState> = _appState.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.FetchAllProducts -> fetchProducts()
            is SearchEvent.FetchSearchProducts -> fetchSearchedProducts(search = event.search)
            is SearchEvent.MoveToDetailed -> navigateToDetailed(id = event.id)
            is SearchEvent.ResetErrorMessage -> errorMessage(message = null)
            is SearchEvent.SaveProduct -> saveProductInDatabase(product = event.product)
            is SearchEvent.ChangeTheme -> setLightTheme(isLight = event.isLight)
            is SearchEvent.ChangeLanguage -> changeLanguage(isGeorgian = event.isGeorgian)
        }
    }

    init {
        getTheme()
        getLanguage()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        _searchState.update { currentState ->
                            currentState.copy(productsList = it.data.products.map { detailedProduct ->
                                detailedProduct.toPresenter()
                            })
                        }
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _searchState.update { currentState ->
                            currentState.copy(isLoading = it.loading)
                        }
                    }
                }
            }
        }
    }

    private var searchNameJob: Job? = null

    private fun fetchSearchedProducts(search: String) {
        with(search) {
            when {
                isBlank() -> fetchProducts()
                length > 1 -> {
                    searchNameJob?.cancel()
                    searchNameJob = viewModelScope.launch {
                        delay(500)
                        getProductSearchUseCase(search = search).collect {
                            when (it) {
                                is Resource.Success -> {
                                    _searchState.update { currentState ->
                                        currentState.copy(productsList = it.data.toPresenter().products)
                                    }
                                }

                                is Resource.Error -> {
                                    errorMessage(message = it.errorMessage)
                                }

                                is Resource.Loading -> {
                                    _searchState.update { currentState ->
                                        currentState.copy(isLoading = it.loading)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun errorMessage(message: String?) {
        _searchState.update { currentState -> currentState.copy(errorMessage = message) }
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

    private fun navigateToDetailed(id: Int) {
        viewModelScope.launch {
            _uiEvent.emit(SearchUIEvent.NavigateToDetailed(id = id))
        }
    }

    private fun saveProductInDatabase(product: ProductCommonDetailed) {
        viewModelScope.launch {
            insertProductInLocalUseCase(product = product.toDomain())
        }
    }

    sealed interface SearchUIEvent {
        data class NavigateToDetailed(val id: Int) : SearchUIEvent
    }

}
