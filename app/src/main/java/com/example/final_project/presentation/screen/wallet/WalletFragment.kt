package com.example.final_project.presentation.screen.wallet

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentWalletBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.wallet.WalletEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {
    private val viewModel: WalletViewModel by viewModels()

    override fun bind() {
    }

    override fun bindListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                viewModel.onEvent(WalletEvent.NavigateToProfile)
            }

            btnAddCard.setOnClickListener {
                viewModel.onEvent(WalletEvent.OpenBottomSheetFragment)
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun handleUiEvent(event: WalletViewModel.WalletUiEvent) {
        when (event) {
            is WalletViewModel.WalletUiEvent.NavigateToProfile -> navigateToProfile()
            is WalletViewModel.WalletUiEvent.OpenBottomSheetFragment -> openBottomSheetFragment()
        }
    }

    private fun openBottomSheetFragment() {
        findNavController().navigate(WalletFragmentDirections.actionWalletFragmentToCardBottomSheetFragment())
    }

    private fun navigateToProfile() {
        val action = WalletFragmentDirections.actionWalletFragmentToProfileFragment()
        findNavController().navigate(action)
    }

}
