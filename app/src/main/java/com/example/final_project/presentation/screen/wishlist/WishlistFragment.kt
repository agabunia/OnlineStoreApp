package com.example.final_project.presentation.screen.wishlist

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentWishlistBinding
import com.example.final_project.presentation.adapter.wishlist.WishlistProductRecyclerAdapter
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.wishlist.WishlistEvent
import com.example.final_project.presentation.screen.product.ProductDetailedFragmentDirections
import com.example.final_project.presentation.state.wishlist.WishlistState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding>(FragmentWishlistBinding::inflate) {
    private lateinit var wishlistProductRecyclerAdapter: WishlistProductRecyclerAdapter
    private val viewModel: WishlistViewModel by viewModels()

    private var amount: Int = 0

    override fun bind() {
        setWishlistProductAdapter()
    }

    override fun bindListeners() {
        binding.btnDeleteAll.setOnClickListener {
            viewModel.onEvent(WishlistEvent.DeleteAllItem)
        }

        binding.btnBuyNow.setOnClickListener {
            viewModel.onEvent(WishlistEvent.BuyProduct(amount))
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wishlistState.collect {
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

    private fun setWishlistProductAdapter() {
        wishlistProductRecyclerAdapter = WishlistProductRecyclerAdapter()
        wishlistProductRecyclerAdapter.apply {
            onDeleteClick = { viewModel.onEvent(WishlistEvent.DeleteItem(it)) }
            onMinusClick = { viewModel.onEvent(WishlistEvent.DecreaseItemQuantity(it)) }
            onPlusClick = { viewModel.onEvent(WishlistEvent.IncreaseItemQuantity(it)) }
        }
        binding.apply {
            rvWishlistProducts.layoutManager = LinearLayoutManager(requireContext())
            rvWishlistProducts.setHasFixedSize(true)
            rvWishlistProducts.adapter = wishlistProductRecyclerAdapter
        }
        viewModel.onEvent(WishlistEvent.FetchAllProducts)
    }

    private fun handleState(state: WishlistState) {
        state.productsList?.let {
            if (it.isEmpty()) {
                binding.apply {
                    rvWishlistProducts.visibility = View.GONE
                    layoutEmptyWishlist.visibility = View.VISIBLE
                    btnBuyNow.isClickable = false
                    btnBuyNow.setBackgroundResource(R.drawable.delete_all_button_shape)
                }

            } else {
                binding.apply {
                    rvWishlistProducts.visibility = View.VISIBLE
                    layoutEmptyWishlist.visibility = View.GONE
                }
                wishlistProductRecyclerAdapter.submitList(it)
            }
        }

        state.errorMessage?.let {
            toastMessage(it)
            viewModel.onEvent(WishlistEvent.ResetErrorMessage)
        }

        state.productsTotalSum?.let {
            val buttonText = "${resources.getText(R.string.buy_now_price)} $it"
            binding.btnBuyNow.text = buttonText
            amount = it
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleUiEvent(event: WishlistViewModel.UIEvent) {
        when (event) {
            is WishlistViewModel.UIEvent.navigateToPayment -> navigateToPayment(event.isSuccessful)
        }
    }

    private fun navigateToPayment(isSuccessful: Boolean) {
        val action =
            WishlistFragmentDirections.actionWishlistFragmentToPaymentFragment(
                isSuccessful
            )
        findNavController().navigate(action)
    }

}