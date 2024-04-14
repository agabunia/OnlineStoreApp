package com.example.final_project.presentation.screen.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.presentation.event.wallet.WalletEvent
import com.example.final_project.presentation.screen.wishlist.WishlistViewModel
import com.example.final_project.presentation.state.wishlist.WishlistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(): ViewModel() {

    private val _wishlistState = MutableStateFlow(WishlistState())
    val wishlistState: SharedFlow<WishlistState> = _wishlistState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<WalletUiEvent>()
    val uiEvent: SharedFlow<WalletUiEvent> get() = _uiEvent

    fun onEvent(event: WalletEvent) {
        when(event) {
            is WalletEvent.NavigateToProfile -> navigateToProfile()
            is WalletEvent.OpenBottomSheetFragment -> openBottomFragment()
        }
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
        object OpenBottomSheetFragment: WalletUiEvent()
    }

}