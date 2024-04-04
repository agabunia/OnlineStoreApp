package com.example.final_project.presentation.screen.payment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.final_project.databinding.FragmentPaymentBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.payment.PaymentEvent
import com.example.final_project.presentation.state.payment.PaymentState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {
    private val isSuccessfulArgs: PaymentFragmentArgs by navArgs()
    private val viewModel: PaymentViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(PaymentEvent.ProcessPayment(isSuccessfulArgs.isSuccessful))
    }

    override fun bindListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(PaymentEvent.NavigateBack)
        }

        binding.btnBackToHome.setOnClickListener {
            viewModel.onEvent(PaymentEvent.NavigateToHome)
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.paymentState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun handleState(state: PaymentState) {
        state.isPaymentSuccessful?.let {
            if (it) {
                binding.layoutFailed.visibility = View.GONE
                binding.layoutSuccessful.visibility = View.VISIBLE
            } else {
                binding.layoutSuccessful.visibility = View.GONE
                binding.layoutFailed.visibility = View.VISIBLE
            }
        }
    }

    private fun handleUiEvent(event: PaymentViewModel.UIEvent) {
        when (event) {
            is PaymentViewModel.UIEvent.NavigateToHome -> navigateToHome()
            is PaymentViewModel.UIEvent.NavigateBack -> goBack()
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun navigateToHome() {
        findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToHomeFragment())
    }

}