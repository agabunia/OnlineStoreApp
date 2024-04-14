package com.example.final_project.presentation.screen.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentSplashBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.state.app_state.AppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()

    override fun bind() {
    }

    override fun bindListeners() {
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvent(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.appState.collect {
                    handleAppStateChange(it)
                }
            }
        }
    }

    private fun handleNavigationEvent(event: SplashViewModel.SplashUIEvent) {
        when (event) {
            is SplashViewModel.SplashUIEvent.NavigateToLogin -> navigateToLogin()
            is SplashViewModel.SplashUIEvent.NavigateToMain -> navigateToMain()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }

    private fun navigateToMain() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
    }

    private fun handleAppStateChange(state: AppState) {
        if (state.isLight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}