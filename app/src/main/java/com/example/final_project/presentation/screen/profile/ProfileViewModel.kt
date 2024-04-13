package com.example.final_project.presentation.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.clear.ClearDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.profile_image.ReadUserUidUseCase
import com.example.final_project.domain.remote.usecase.profile.UploadProfileImageUseCase
import com.example.final_project.domain.remote.usecase.profile.GetProfileImageUseCase
import com.example.final_project.presentation.event.profile.ProfileEvent
import com.example.final_project.presentation.state.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearDataStoreUseCase: ClearDataStoreUseCase,
    private val changeProfileImageUseCase: UploadProfileImageUseCase,
    private val getUserProfileImageUseCase: GetProfileImageUseCase,
    private val readUserUidUseCase: ReadUserUidUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: SharedFlow<ProfileState> = _profileState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> get() = _uiEvent

    fun onEvent(event: ProfileEvent) {
        viewModelScope.launch {
            when (event) {
                is ProfileEvent.LogOut -> logout()
                is ProfileEvent.Terms -> navigateToTerms()
                is ProfileEvent.UploadImage -> uploadImage(uri = event.uri)
                is ProfileEvent.GetProfileImage -> getProfileImage()
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            clearDataStoreUseCase()
            _uiEvent.emit(UiEvent.NavigateToLogin)
        }
    }

    private fun navigateToTerms() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateToTerms)
        }
    }

    private fun getProfileImage() {
        viewModelScope.launch {
            val uid = readUserUidUseCase().first()

            getUserProfileImageUseCase(uid = uid).collect {
                when (it) {
                    is Resource.Success -> _profileState.update { currentState ->
                        currentState.copy(userImage = currentState.userImage)
                    }

                    is Resource.Error -> errorMessage(message = it.errorMessage)
                    is Resource.Loading -> _profileState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }
                }
            }
        }
    }

    private suspend fun uploadImage(uri: Uri) {
        val uid = readUserUidUseCase().first()
        changeProfileImageUseCase(uri = uri, uid = uid).collect {}
    }

    private fun errorMessage(message: String?) {
        _profileState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface UiEvent {
        object NavigateToLogin : UiEvent
        object NavigateToTerms : UiEvent
    }

}