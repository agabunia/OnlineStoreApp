package com.example.final_project.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.authorization.SaveDataStoreUseCase
import com.example.final_project.domain.remote.usecase.registration.RegistrationUseCase
import com.example.final_project.domain.remote.usecase.validators.user.EmailValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.user.PasswordRepeatValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.user.PasswordValidatorUseCase
import com.example.final_project.presentation.event.registration.RegistrationEvent
import com.example.final_project.presentation.state.registration.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val passwordRepeatValidatorUseCase: PasswordRepeatValidatorUseCase,
    private val saveDataStoreUseCase: SaveDataStoreUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegistrationState())
    val registerState: SharedFlow<RegistrationState> = _registerState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RegistrationUIEvent>()
    val uiEvent: SharedFlow<RegistrationUIEvent> get() = _uiEvent

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Register -> {
                register(
                    email = event.email,
                    password = event.password,
                    passwordCheck = event.passwordCheck
                )
            }

            is RegistrationEvent.Login -> navigateToLogin()

            is RegistrationEvent.ResetErrorMessage -> {
                errorMessage(message = null)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            registrationUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Success -> {
                        saveDataStoreUseCase(it.data.toString())
                        _registerState.update { currentState -> currentState.copy(data = it.data.toString()) }
                        _uiEvent.emit(RegistrationUIEvent.NavigateToMain)
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _registerState.update { currentState -> currentState.copy(isLoading = it.loading) }
                    }
                }
            }
        }
    }

    private fun register(email: String, password: String, passwordCheck: String) {
        val isEmailValid = emailValidatorUseCase(email)
        val isPasswordValid = passwordValidatorUseCase(password)
        val isPasswordEnteredCorrect = passwordRepeatValidatorUseCase(password, passwordCheck)

        // ესენი გასატანია ცალკე სტრინგ ველიუებში რომ თარგმნადი იყოს!!
        if (!isEmailValid) {
            errorMessage(message = "Email  is not valid!")
        } else if (!isPasswordValid) {
            errorMessage(message = "Password is not valid!")
        } else if (!isPasswordEnteredCorrect) {
            errorMessage(message = "Passwords do not match!")
        } else {
            registerUser(email = email, password = password)
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _uiEvent.emit(RegistrationUIEvent.NavigateToLogin)
        }
    }

    private fun errorMessage(message: String?) {
        _registerState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface RegistrationUIEvent {
        object NavigateToLogin : RegistrationUIEvent
        object NavigateToMain : RegistrationUIEvent
    }

}