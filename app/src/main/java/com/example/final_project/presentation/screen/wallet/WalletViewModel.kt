package com.example.final_project.presentation.screen.wallet

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.profile_image.ReadUserUidUseCase
import com.example.final_project.domain.remote.usecase.wallet.DeleteCardUseCase
import com.example.final_project.domain.remote.usecase.wallet.GetAllCardsUseCase
import com.example.final_project.presentation.event.wallet.WalletEvent
import com.example.final_project.presentation.mapper.wallet.toPresenter
import com.example.final_project.presentation.screen.wishlist.WishlistViewModel
import com.example.final_project.presentation.state.wallet.WalletState
import com.example.final_project.presentation.state.wishlist.WishlistState
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
class WalletViewModel @Inject constructor(
    private val readUserUidUseCase: ReadUserUidUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow(WalletState())
    val walletState: SharedFlow<WalletState> = _walletState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<WalletUiEvent>()
    val uiEvent: SharedFlow<WalletUiEvent> get() = _uiEvent

    fun onEvent(event: WalletEvent) {
        when (event) {
            is WalletEvent.GetAllCards -> getAllCards()
            is WalletEvent.NavigateToProfile -> navigateToProfile()
            is WalletEvent.OpenBottomSheetFragment -> openBottomFragment()
            is WalletEvent.ResetErrorMessage -> errorMessage(message = null)
            is WalletEvent.DeleteCard -> deleteCard(id = event.id)
        }
    }

    private fun getAllCards() {
        viewModelScope.launch {
            val uid = readUserUidUseCase().first()
            getAllCardsUseCase(uid).collect {
                when (it) {
                    is Resource.Success -> {
                        _walletState.update { currentState ->
                            currentState.copy(allCards = it.data.map { card ->
                                card.toPresenter()
                            })
                        }
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> _walletState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }
                }
            }
        }
    }

    private fun deleteCard(id: String) {
        viewModelScope.launch {
            val uid = readUserUidUseCase().first()
            deleteCardUseCase(uid = uid, cardId = id).collect{
                when(it) {
                    is Resource.Success -> getAllCards()
                    is Resource.Error -> errorMessage(message = it.errorMessage)
                    is Resource.Loading -> _walletState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }
                }
            }
        }
    }

    private fun errorMessage(message: String?) {
        _walletState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    private fun navigateToProfile() {
        viewModelScope.launch {
            _uiEvent.emit(WalletUiEvent.NavigateToProfile)
        }
    }

    private fun openBottomFragment() {
        viewModelScope.launch {
            _uiEvent.emit(WalletUiEvent.OpenBottomSheetFragment)
        }
    }

    sealed class WalletUiEvent() {
        object NavigateToProfile : WalletUiEvent()
        object OpenBottomSheetFragment : WalletUiEvent()
    }

}