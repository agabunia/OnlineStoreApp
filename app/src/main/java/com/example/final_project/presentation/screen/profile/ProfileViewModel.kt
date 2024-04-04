package com.example.final_project.presentation.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.common.Resource
import com.example.final_project.domain.local.usecase.datastore.clear.ClearDataStoreUseCase
import com.example.final_project.domain.remote.usecase.profile.ChangeProfileImageUseCase
import com.example.final_project.domain.remote.usecase.profile.GetUserProfileImageUseCase
import com.example.final_project.presentation.event.profile.ProfileEvent
import com.example.final_project.presentation.state.home.HomeState
import com.example.final_project.presentation.state.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearDataStoreUseCase: ClearDataStoreUseCase,
    private val changeProfileImageUseCase: ChangeProfileImageUseCase,
    private val getUserProfileImageUseCase: GetUserProfileImageUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: SharedFlow<ProfileState> = _profileState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> get() = _uiEvent

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogOut -> logout()
            is ProfileEvent.Terms -> navigateToTerms()
            is ProfileEvent.ChangeImage -> changeProfileImage(uri = event.uri)
//            is ProfileEvent.GetUserProfileImage -> getUserProfileImage()
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

    private fun changeProfileImage(uri: Uri) {
        viewModelScope.launch {
            changeProfileImageUseCase(uri).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _profileState.update { it.copy(userImage = resource.data.user.photoUrl) }
                    }

                    is Resource.Error -> errorMessage(resource.errorMessage)
                    is Resource.Loading -> _profileState.update { it.copy(isLoading = resource.loading) }
                }
            }
        }
    }

//    private fun getUserProfileImage() {
//        viewModelScope.launch {
//            getUserProfileImageUseCase().collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        _profileState.update { it.copy(userImage = resource.data) }
//                    }
//                    is Resource.Error -> errorMessage(resource.errorMessage)
//                    is Resource.Loading -> _profileState.update { it.copy(isLoading = resource.loading) }
//                }
//            }
//        }
//    }

    private fun errorMessage(message: String?) {
        _profileState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface UiEvent {
        object NavigateToLogin : UiEvent
        object NavigateToTerms : UiEvent
    }

}