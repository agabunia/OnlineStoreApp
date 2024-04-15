package com.example.final_project.presentation.bottom_sheet_fragment.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.presentation.event.bottom_sheet_fragment.card.CardEvent
import com.example.final_project.presentation.model.wallet.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardBottomSheetFragmentViewModel @Inject constructor(): ViewModel() {

//    private val _cardState = MutableStateFlow()
//    val cardState: SharedFlow<> = _cardState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CardUiEvent>()
    val uiEvent: SharedFlow<CardUiEvent> get() = _uiEvent


    fun onEvent(event: CardEvent) {
        when(event) {
            is CardEvent.AddCard -> addCard(newCard = event.newCard)
        }
    }

    private fun addCard(newCard: Card) {
        navigateToWallet()
    }

    private fun navigateToWallet() {
        viewModelScope.launch {
            _uiEvent.emit(CardUiEvent.NavigateToWallet)
        }
    }

    sealed interface CardUiEvent {
        object NavigateToWallet: CardUiEvent
    }


}