package com.example.final_project.presentation.screen.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.activity.MainActivity
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.profile.ProfileEvent
import com.example.final_project.presentation.extention.loadImage
import com.example.final_project.presentation.state.profile.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data

                selectedImageUri?.let { uri ->
                    viewModel.onEvent(ProfileEvent.UploadImage(uri = uri))
                    Glide.with(requireContext()).load(uri).into(binding.ivProfileImage)
                }
            }
        }

    override fun bind() {
        (activity as? MainActivity)?.showBottomNavigationBar()
        viewModel.onEvent(ProfileEvent.GetProfileImage)
    }

    override fun bindListeners() {
        binding.layoutLogout.setOnClickListener {
            viewModel.onEvent(ProfileEvent.LogOut)
        }

        binding.layoutTerms.setOnClickListener {
            viewModel.onEvent(ProfileEvent.Terms)
        }

        binding.btnEditImage.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(galleryIntent)
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
            binding.ivProfileImage.loadImage(it)
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

}