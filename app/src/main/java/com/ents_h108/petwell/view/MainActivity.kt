package com.ents_h108.petwell.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.ActivityMainBinding
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        authViewModel.getUsername().observe(this) {
            binding.profileUserName.text = it.toString()
        }

        navController.addOnDestinationChangedListener { _, nd, _ ->
            binding.apply {
                when (nd.id) {
                    R.id.registerFragment, R.id.loginFragment, R.id.onboardingFragment, R.id.forgotPasswordFragment, R.id.editProfileFragment,R.id.editPetFragment -> {
                        topAppBar.visibility = View.GONE
                        bottomNavigation.visibility = View.GONE
                        userDetail.visibility = View.GONE
                        locationDetail.visibility = View.GONE
                    }

                    R.id.profileFragment, R.id.promoFragment, R.id.historyFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.VISIBLE
                        userDetail.visibility = View.GONE
                        locationDetail.visibility = View.GONE
                        topAppBar.title = when (nd.id) {
                            R.id.profileFragment -> "Profile"
                            R.id.promoFragment -> "Promo"
                            R.id.historyFragment -> "History"
                            else -> getString(R.string.app_name)
                        }
                    }

                    else -> {
                        topAppBar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.VISIBLE
                        userDetail.visibility = View.VISIBLE
                        locationDetail.visibility = View.VISIBLE
                        topAppBar.title = null
                    }
                }
            }
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.history -> {
                    navController.navigate(R.id.historyFragment)
                    true
                }

                R.id.promo -> {
                    navController.navigate(R.id.promoFragment)
                    true
                }

                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == R.id.homeFragment || navController.currentDestination?.id == R.id.onboardingFragment) {
                    finish()
                }
                if (navController.currentDestination?.id == R.id.registerFragment || navController.currentDestination?.id == R.id.loginFragment) {
                    navController.navigate(R.id.onboardingFragment)
                } else {
                    navController.popBackStack()
                }
            }
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            btmNavTrue(destination.id)
        }

    }

    private fun btmNavTrue(destinationId: Int) {
        when (destinationId) {
            R.id.homeFragment -> binding.bottomNavigation.menu.findItem(R.id.home).isChecked = true
            R.id.historyFragment -> binding.bottomNavigation.menu.findItem(R.id.history).isChecked =
                true

            R.id.promoFragment -> binding.bottomNavigation.menu.findItem(R.id.promo).isChecked =
                true

            R.id.profileFragment -> binding.bottomNavigation.menu.findItem(R.id.profile).isChecked =
                true
        }
    }

}
