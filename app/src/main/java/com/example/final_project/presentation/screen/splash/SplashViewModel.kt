package com.example.final_project.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.local.usecase.datastore.authorization.ReadDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.theme.GetThemeDataStoreUseCase
import com.example.final_project.presentation.state.app_state.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val readDataStoreUseCase: ReadDataStoreUseCase,
    private val getThemeDataStoreUseCase: GetThemeDataStoreUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUIEvent>()
    val uiEvent: SharedFlow<SplashUIEvent> get() = _uiEvent

    private val _appState = MutableStateFlow(AppState())
    val appState: SharedFlow<AppState> = _appState.asStateFlow()

    init {
        getTheme()
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            readDataStoreUseCase().collect {
                if (it.isEmpty()) {
                    _uiEvent.emit(SplashUIEvent.NavigateToLogin)
                } else {
                    _uiEvent.emit(SplashUIEvent.NavigateToMain)
                }
            }
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


    sealed interface SplashUIEvent {
        object NavigateToLogin : SplashUIEvent
        object NavigateToMain : SplashUIEvent
    }
}