package com.example.final_project.presentation.screen.wallet

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.final_project.databinding.FragmentWalletBinding
import com.example.final_project.presentation.adapter.wallet.CardViewPagerAdapter
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.wallet.WalletEvent
import com.example.final_project.presentation.state.wallet.WalletState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {
    private val viewModel: WalletViewModel by viewModels()
    private lateinit var cardViewPagerAdapter: CardViewPagerAdapter

    override fun bind() {
        setCardViewPagerAdapter()
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.walletState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: WalletState) {
        state.allCards?.let {
            if (it.isEmpty()) {
                binding.ivEmptyCards.visibility = View.VISIBLE
            }
            binding.ivEmptyCards.visibility = View.GONE
            cardViewPagerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            toastMessage(message = it)
            viewModel.onEvent(WalletEvent.ResetErrorMessage)
        }
    }

    private fun setCardViewPagerAdapter() {
        cardViewPagerAdapter = CardViewPagerAdapter()
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.vpCard.apply {
            adapter = cardViewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer(transformer)
        }
        viewModel.onEvent(WalletEvent.GetAllCards)
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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
