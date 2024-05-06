package com.example.final_project.presentation.screen.product

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.final_project.databinding.FragmentProductDetailedBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.adapter.product.ImageSlideViewPagerAdapter
import com.example.final_project.presentation.adapter.product.StarRecyclerAdapter
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.product.ProductEvent
import com.example.final_project.presentation.model.product.ProductDetailed
import com.example.final_project.presentation.state.product.ProductState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailedFragment :
    BaseFragment<FragmentProductDetailedBinding>(FragmentProductDetailedBinding::inflate) {
    private val viewModel: ProductDetailedViewModel by viewModels()
    private lateinit var imageSlideViewPagerAdapter: ImageSlideViewPagerAdapter
    private lateinit var starRecyclerAdapter: StarRecyclerAdapter
    private val productArgs: ProductDetailedFragmentArgs by navArgs()

    override fun bind() {
        viewModel.onEvent(ProductEvent.FetchProductDetailed(productArgs.id))
    }

    override fun bindListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(ProductEvent.NavigateBack)
        }

        binding.btnMinus.setOnClickListener {
            val quantity = binding.tvQuantity.text.toString().toInt()
            viewModel.onEvent(ProductEvent.DecreaseQuantity(quantity = quantity))
        }

        binding.btnPlus.setOnClickListener {
            val quantity = binding.tvQuantity.text.toString().toInt()
            val stock = binding.tvProductStock.text.toString().toInt()
            viewModel.onEvent(ProductEvent.IncreaseQuantity(quantity = quantity, stock = stock))
        }

        binding.btnBuyNow.setOnClickListener {
            val quantity = binding.tvQuantity.text.toString().toInt()
            val amount = binding.tvProductPrice.text.toString().toInt()
            viewModel.onEvent(ProductEvent.BuyProduct(quantity * amount))
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productState.collect {
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

    private fun handleState(state: ProductState) {
        state.productDetails?.let {
            bindProductItems(it)
        }

        state.quantity?.let {
            binding.tvQuantity.text = it.toString()
        }

        state.errorMessage?.let {
            toastMessage(it)
            viewModel.onEvent(ProductEvent.ResetErrorMessage)
        }

        binding.progressBar.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE
    }

    private fun bindProductItems(product: ProductDetailed) {
        binding.apply {
            tvProductTitle.text = product.title
            tvProductBrand.text = product.brand
            tvProductCategory.text = product.category
            tvProductPrice.text = product.price.toString()
            tvProductDescription.text = product.description
            tvProductDiscountRate.text = product.discountPercentage.toString()
            tvProductStock.text = product.stock.toString()

            setViewPagerAdapter(product.images)
            setStarRecyclerAdapter(product.rating)

            btnAddToWishlist.setOnClickListener {
                viewModel.onEvent(ProductEvent.SaveProduct(product))
            }
        }
    }

    private fun setViewPagerAdapter(images: List<String>) {
        imageSlideViewPagerAdapter = ImageSlideViewPagerAdapter(images)
        binding.apply {
            viewPager.adapter = imageSlideViewPagerAdapter
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun setStarRecyclerAdapter(stars: Int) {
        starRecyclerAdapter = StarRecyclerAdapter(stars)
        binding.apply {
            rvStar.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvStar.setHasFixedSize(true)
            rvStar.adapter = starRecyclerAdapter
        }
    }


    private fun handleUiEvent(event: ProductDetailedViewModel.UIEvent) {
        when (event) {
            is ProductDetailedViewModel.UIEvent.NavigateToPayment -> navigateToPayment(event.isSuccessful)
            is ProductDetailedViewModel.UIEvent.NavigateBack -> goBack()
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun navigateToPayment(isSuccessful: Boolean) {
        val action =
            ProductDetailedFragmentDirections.actionProductDetailedFragmentToPaymentFragment(
                isSuccessful
            )
        findNavController().navigate(action)
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}