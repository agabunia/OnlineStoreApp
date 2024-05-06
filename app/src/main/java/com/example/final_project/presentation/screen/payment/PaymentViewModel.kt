package com.example.final_project.presentation.screen.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.local.usecase.db_manipulators.DeleteAllLocalProductsUseCase
import com.example.final_project.presentation.event.payment.PaymentEvent
import com.example.final_project.presentation.state.payment.PaymentState
import com.example.final_project.presentation.state.product.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val deleteAllLocalProductsUseCase: DeleteAllLocalProductsUseCase
) : ViewModel() {

    private val _paymentState = MutableStateFlow(PaymentState())
    val paymentState: SharedFlow<PaymentState> = _paymentState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> get() = _uiEvent

    fun onEvent(event: PaymentEvent) {
        when (event) {
            is PaymentEvent.ProcessPayment -> processPayment(isSuccessful = event.isSuccessful)
            is PaymentEvent.NavigateToHome -> navigateToHome()
            is PaymentEvent.NavigateBack -> navigateBack()
        }
    }

    private fun processPayment(isSuccessful: Boolean) {
        viewModelScope.launch {
            _paymentState.update { currentState ->
                currentState.copy(isPaymentSuccessful = isSuccessful)
            }
            if (isSuccessful) {
                deleteAllLocalProductsUseCase()
            }
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.NavigateToHome)
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.NavigateBack)
        }
    }

    sealed interface UIEvent {
        object NavigateToHome : UIEvent
        object NavigateBack : UIEvent
    }
}