package com.ents_h108.petwell.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, nd, _ ->
            binding.apply {
                when (nd.id) {
                    R.id.registerFragment, R.id.loginFragment, R.id.onboardingFragment -> {
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
                        topAppBar.title = getString(R.string.app_name)
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
    }
}
