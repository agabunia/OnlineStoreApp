package com.example.final_project.presentation.bottom_sheet_fragment.card


import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentCardBottomSheetBinding
import com.example.final_project.presentation.base.BaseBottomSheetFragment
import com.example.final_project.presentation.event.bottom_sheet_fragment.card.CardEvent
import com.example.final_project.presentation.extention.loadImage
import com.example.final_project.presentation.extention.loadImageId
import com.example.final_project.presentation.model.wallet.Card
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardBottomSheetFragment :
    BaseBottomSheetFragment<FragmentCardBottomSheetBinding>(FragmentCardBottomSheetBinding::inflate) {
    private val viewModel: CardBottomSheetFragmentViewModel by viewModels()

    override fun bind() {
    }

    override fun bindListeners() {
        with(binding) {
            etCardNumber.doAfterTextChanged { text ->
                val formattedText = text.toString().replace(" ", "").chunked(4).joinToString(" ")

                if (formattedText != text.toString()) {
                    etCardNumber.setText(formattedText)
                    etCardNumber.setSelection(etCardNumber.length())
                }
            }

            setCardTypeImage()

            btnAddCard.setOnClickListener {
                viewModel.onEvent(
                    CardEvent.AddCard(
                        Card(
                            id = etCardNumber.text.toString().replace(" ", ""),
                            cardNumber = etCardNumber.text.toString(),
                            date = etExpirationDate.text.toString(),
                            cvv = etCvv.text.toString(),
                            cardType = setCardType()
                        )
                    )
                )
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect{
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun setCardTypeImage() {
        val number = binding.etCardNumber.text

        val drawableString = if (number.isNullOrBlank()) {
            R.drawable.ic_card_other
        } else {
            when (number.toString()[0]) {
                '4' -> R.drawable.ic_card_visa
                '2', '5' -> R.drawable.ic_card_mc
                '3' -> R.drawable.ic_card_amex
                else -> R.drawable.ic_card_other
            }
        }

        val drawable = ContextCompat.getDrawable(requireContext(), drawableString)

        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    private fun setCardType(): Card.CardType {
        val number = binding.etCardNumber.text.toString()[0]
        return when (number) {
            '4' -> Card.CardType.VISA
            '2', '5' -> Card.CardType.MASTER_CARD
            '3' -> Card.CardType.AMERICAN_EXPRESS
            else -> Card.CardType.OTHER
        }
    }

    private fun handleUiEvent(event: CardBottomSheetFragmentViewModel.CardUiEvent) {
        when(event) {
            is CardBottomSheetFragmentViewModel.CardUiEvent.NavigateToWallet -> navigateToWallet()
        }
    }

    private fun navigateToWallet() {
        findNavController().navigate(CardBottomSheetFragmentDirections.actionCardBottomSheetFragmentToWalletFragment())
    }


}
