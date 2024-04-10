package com.example.final_project.presentation.screen.search

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.os.LocaleListCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentSearchBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.adapter.common_product_adapter.ProductRecyclerAdapter
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.event.search.SearchEvent
import com.example.final_project.presentation.state.app_state.AppState
import com.example.final_project.presentation.state.search.SearchState
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var productRecyclerAdapter: ProductRecyclerAdapter
    private lateinit var switchSearchTheme: SwitchCompat
    private lateinit var switchSearchLanguage: SwitchCompat

    override fun bind() {
        (activity as? MainActivity)?.showBottomNavigationBar()
        setProductAdapter()
        setChangeSwitch()
    }

    override fun bindListeners() {
        binding.etSearch.doAfterTextChanged {
            val search = binding.etSearch.text.toString()
            viewModel.onEvent(SearchEvent.FetchSearchProducts(search))
        }

        switchSearchTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isLight = isChecked)
        }

        switchSearchLanguage.setOnCheckedChangeListener { _, isChecked ->
            changeLanguage(isGeorgian = isChecked)
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchState.collect {
                    handleState(it)
                }
            }
        }

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

    private fun setProductAdapter() {
        productRecyclerAdapter = ProductRecyclerAdapter()
        productRecyclerAdapter.onItemClick = {
            viewModel.onEvent(SearchEvent.MoveToDetailed(it))
        }
        productRecyclerAdapter.saveProductClick = {
            viewModel.onEvent(SearchEvent.SaveProduct(it))
        }
        binding.apply {
            rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
            rvProduct.setHasFixedSize(true)
            rvProduct.adapter = productRecyclerAdapter
        }
        viewModel.onEvent(SearchEvent.FetchAllProducts)
    }

    private fun handleState(state: SearchState) {
        state.productsList?.let {
            productRecyclerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            toastMessage(it)
            viewModel.onEvent(SearchEvent.ResetErrorMessage)
        }

        state.isLoading.let {
            binding.apply {
                if (it) {
                    rvProduct.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                    rvProduct.visibility = View.VISIBLE
                }
            }
        }

        binding.progressBar.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE
    }

    private fun changeTheme(isLight: Boolean) {
        viewModel.onEvent(SearchEvent.ChangeTheme(isLight = isLight))
    }

    private fun changeLanguage(isGeorgian: Boolean) {
        viewModel.onEvent(SearchEvent.ChangeLanguage(isGeorgian = isGeorgian))
    }

    private fun setChangeSwitch() {
        val navigationView = activity?.findViewById<NavigationView>(R.id.drawerSearchMenu)

        switchSearchTheme =
            navigationView?.menu?.findItem(R.id.themeMode)?.actionView?.findViewById(R.id.switchTheme)!!
        switchSearchLanguage =
            navigationView.menu.findItem(R.id.language)?.actionView?.findViewById(R.id.switchLanguage)!!
    }

    private fun handleAppStateChange(state: AppState) {
        switchSearchTheme.isChecked = state.isLight
        switchSearchLanguage.isChecked = state.isGeorgian

        if (state.isLight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        changeLanguageConfig(state.isGeorgian)
    }

    private fun changeLanguageConfig(isGeorgian: Boolean) {
        val language = if (isGeorgian) "ka" else "en"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context?.getSystemService(LocaleManager::class.java)
                ?.applicationLocales = LocaleList.forLanguageTags(language)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(
                    language
                )
            )
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleNavigationEvent(event: SearchViewModel.SearchUIEvent) {
        when (event) {
            is SearchViewModel.SearchUIEvent.NavigateToDetailed -> navigateToProductDetails(id = event.id)
        }
    }

    private fun navigateToProductDetails(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailedFragment(id = id)
        findNavController().navigate(action)
    }

}
