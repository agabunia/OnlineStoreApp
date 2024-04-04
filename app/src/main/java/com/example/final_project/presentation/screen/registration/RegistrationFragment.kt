package com.example.final_project.presentation.screen.registration

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentRegistrationBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.registration.RegistrationEvent
import com.example.final_project.presentation.state.registration.RegistrationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun bind() {
        (activity as? MainActivity)?.hideBottomNavigationBar()
    }

    override fun bindListeners() {
        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.btnBack.setOnClickListener {
            login()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun register() {
        viewModel.onEvent(
            RegistrationEvent.Register(
                email = binding.etEmailInput.text.toString(),
                password = binding.etPasswordInput.text.toString(),
                passwordCheck = binding.etPasswordInputCheck.text.toString()
            )
        )
    }

    private fun login() {
        viewModel.onEvent(RegistrationEvent.Login)
    }

    private fun handleState(state: RegistrationState) {
        binding.progressBar.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.let {
            toastMessage(it)
            viewModel.onEvent(RegistrationEvent.ResetErrorMessage)
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleNavigationEvents(event: RegistrationViewModel.RegistrationUIEvent) {
        when (event) {
            is RegistrationViewModel.RegistrationUIEvent.NavigateToLogin -> navigateToLogin()
            is RegistrationViewModel.RegistrationUIEvent.NavigateToMain -> navigateToMain()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
    }

    private fun navigateToMain() {
        findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment())
    }

}
