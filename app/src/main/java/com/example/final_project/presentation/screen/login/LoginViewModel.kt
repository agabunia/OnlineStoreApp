package com.example.final_project.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.authorization.SaveDataStoreUseCase
import com.example.final_project.domain.remote.usecase.login.LoginUseCase
import com.example.final_project.domain.remote.usecase.validators.EmailValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.PasswordValidatorUseCase
import com.example.final_project.presentation.event.login.LoginEvent
import com.example.final_project.presentation.state.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val saveDataStoreUseCase: SaveDataStoreUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: SharedFlow<LoginState> = _loginState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> get() = _uiEvent

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login(email = event.email, password = event.password, saveUser = event.saveUser)
            }

            is LoginEvent.ResetErrorMessage -> {
                errorMessage(message = null)
            }

            is LoginEvent.Register -> navigateToRegister()
        }
    }

    private fun loginUser(email: String, password: String, saveUser: Boolean) {
        viewModelScope.launch {
            loginUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Success -> {
                        if (saveUser) {
                            saveDataStoreUseCase(it.data.toString())
                        }
                        _loginState.update { currentState -> currentState.copy(data = it.data.toString()) }
                        _uiEvent.emit(LoginUiEvent.NavigateToMain)
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _loginState.update { currentState -> currentState.copy(isLoading = it.loading) }
                    }
                }
            }
        }
    }

    private fun navigateToRegister() {
        viewModelScope.launch {
            _uiEvent.emit(LoginUiEvent.NavigateToRegister)
        }
    }

    private fun login(email: String, password: String, saveUser: Boolean) {
        val isEmailValid = emailValidatorUseCase(email)
        val isPasswordValid = passwordValidatorUseCase(password)

        // გასატანია ცალკე სტრინგ ველიუებში რომ თარგმნადი იყოს!
        if (!isEmailValid) {
            errorMessage(message = "Email  is not valid!")
        } else if (!isPasswordValid) {
            errorMessage(message = "Password is not valid!")
        } else {
            loginUser(email = email, password = password, saveUser = saveUser)
        }
    }

    private fun errorMessage(message: String?) {
        _loginState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface LoginUiEvent {
        object NavigateToMain : LoginUiEvent
        object NavigateToRegister : LoginUiEvent
    }

}
