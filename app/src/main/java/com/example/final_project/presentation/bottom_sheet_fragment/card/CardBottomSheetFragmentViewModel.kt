package com.example.final_project.presentation.bottom_sheet_fragment.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.remote.usecase.validators.card.CardCvvValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.card.CardDateValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.card.CardNumberValidatorUseCase
import com.example.final_project.presentation.event.bottom_sheet_fragment.card.CardEvent
import com.example.final_project.presentation.model.wallet.Card
import com.example.final_project.presentation.state.card.CardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardBottomSheetFragmentViewModel @Inject constructor(
    private val cardNumberValidatorUseCase: CardNumberValidatorUseCase,
    private val cardDateValidatorUseCase: CardDateValidatorUseCase,
    private val cardCvvValidatorUseCase: CardCvvValidatorUseCase
): ViewModel() {

    private val _cardState = MutableStateFlow(CardState())
    val cardState: SharedFlow<CardState> = _cardState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CardUiEvent>()
    val uiEvent: SharedFlow<CardUiEvent> get() = _uiEvent


    fun onEvent(event: CardEvent) {
        when(event) {
            is CardEvent.AddCard -> addCard(newCard = event.newCard)
            is CardEvent.ResetErrorMessage -> errorMessage(message = null)
        }
    }

    private fun addCard(newCard: Card) {
        val isCardNumberValid = cardNumberValidatorUseCase(newCard.cardNumber)
        val isCardDateValid = cardDateValidatorUseCase(newCard.date)
        val isCardCvvValid = cardCvvValidatorUseCase(newCard.cvv)

        if (!isCardNumberValid || !isCardCvvValid) {
            errorMessage(message = "Card Number is not Valid")
        } else if (!isCardDateValid) {
            errorMessage(message = "Card Is Outdated")
        } else {
            errorMessage(message = null)
        }

//        navigateToWallet()
    }

    private fun errorMessage(message: String?) {
        _cardState.update { currentState -> currentState.copy(errorMessage = message) }
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