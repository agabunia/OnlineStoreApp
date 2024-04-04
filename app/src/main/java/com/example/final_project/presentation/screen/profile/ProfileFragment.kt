package com.example.final_project.presentation.screen.profile

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.profile.ProfileEvent
import com.example.final_project.presentation.state.profile.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { viewModel.onEvent(ProfileEvent.ChangeImage(it)) }
        }

    override fun bind() {
        (activity as? MainActivity)?.showBottomNavigationBar()
//        viewModel.onEvent(ProfileEvent.GetUserProfileImage)
    }

    override fun bindListeners() {
        binding.layoutLogout.setOnClickListener {
            viewModel.onEvent(ProfileEvent.LogOut)
        }

        binding.layoutTerms.setOnClickListener {
            viewModel.onEvent(ProfileEvent.Terms)
        }

        binding.btnEditImage.setOnClickListener {
            requestPermission()
            galleryLauncher.launch("image/*")
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleEvent(it)
                }
            }
        }
    }

    private fun handleState(state: ProfileState) {
        state.userImage?.let {
            binding.ivProfileImage.setImageURI(it)
        }

        state.errorMessage?.let {
            toastMessage(it)
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleEvent(event: ProfileViewModel.UiEvent) {
        when (event) {
            is ProfileViewModel.UiEvent.NavigateToLogin -> navigateToLogin()
            is ProfileViewModel.UiEvent.NavigateToTerms -> navigateToTerms()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

    private fun navigateToTerms() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToTermsFragment())
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }

}