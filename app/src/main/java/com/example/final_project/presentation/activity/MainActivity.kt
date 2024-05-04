package com.example.final_project.presentation.activity

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.final_project.R
import com.example.final_project.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestNotificationPermission()
        setContentView(binding.root)
        setNavigation()
        readPushToken()
        navigateToDetailedPageFromNotification()
    }

    private fun setNavigation() {
        with(binding) {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.navController
            bottomNavigation.setupWithNavController(
                navController
            )

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment, R.id.searchFragment, R.id.wishlistFragment, R.id.profileFragment, R.id.paymentFragment -> {
                        bottomNavigation.visibility = View.VISIBLE
                    }

                    else -> bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    private fun readPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                d("Fetching FCM registration token failed", task.exception.toString())
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            d("firebaseToken", token)
        })
    }

    private fun navigateToDetailedPageFromNotification() {
        if (intent.extras != null && intent.hasExtra("id")) {
            val productId = intent.getStringExtra("id")

            if (productId != null) {
                val bundle = Bundle().apply {
                    putInt("id", productId.toInt())
                }
                navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
                navController = navHostFragment.navController
                navController.navigate(
                    R.id.productDetailedFragment,
                    bundle
                )
            }
        }
    }

}
