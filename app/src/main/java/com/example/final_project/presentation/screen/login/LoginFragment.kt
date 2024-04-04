package com.example.final_project.presentation.screen.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentLoginBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.login.LoginEvent
import com.example.final_project.presentation.state.login.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun bind() {
        (activity as? MainActivity)?.hideBottomNavigationBar()
    }

    override fun bindListeners() {
        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.btnLogin.setOnClickListener {
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
                viewModel.loginState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun login() {
        viewModel.onEvent(
            LoginEvent.Login(
                email = binding.etEmailInput.text.toString(),
                password = binding.etPasswordInput.text.toString(),
                saveUser = binding.cbSaveUser.isChecked
            )
        )
    }

    private fun register() {
        viewModel.onEvent(LoginEvent.Register)
    }

    private fun handleState(state: LoginState) {
        binding.progressBar.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.let {
            toastMessage(it)
            viewModel.onEvent(LoginEvent.ResetErrorMessage)
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleNavigationEvents(event: LoginViewModel.LoginUiEvent) {
        when (event) {
            is LoginViewModel.LoginUiEvent.NavigateToMain -> navigateToLogin()
            is LoginViewModel.LoginUiEvent.NavigateToRegister -> navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
    }

    private fun navigateToLogin() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

}
