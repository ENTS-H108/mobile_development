package com.ents_h108.petwell.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()
        setupBottomNavigation()
        setupOnBackPressed()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateUIForDestination(destination.id)
        }
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> navigateToDestination(R.id.homeFragment)
                R.id.history -> navigateToDestination(R.id.historyFragment)
                R.id.promo -> navigateToDestination(R.id.promoFragment)
                R.id.profile -> navigateToDestination(R.id.profileFragment)
                else -> false
            }
        }
    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (navController.currentDestination?.id) {
                    R.id.homeFragment, R.id.onboardingFragment -> {
                        finish()
                    }
                    R.id.registerFragment, R.id.loginFragment -> {
                        navController.navigate(R.id.onboardingFragment)
                    }
                    else -> {
                        navController.popBackStack()
                    }
                }
            }
        })
    }

    private fun navigateToDestination(destinationId: Int): Boolean {
        navController.navigate(destinationId)
        return true
    }

    private fun updateUIForDestination(destinationId: Int) {
        binding.apply {
            topAppBar.visibility = if (destinationId in hiddenTopAppBarDestinations) View.GONE else View.VISIBLE
            bottomNavigation.visibility = if (destinationId in visibleBottomNavDestinations) View.VISIBLE else View.GONE
            topAppBar.title = getTitleForDestination(destinationId)
        }
    }

    private val hiddenTopAppBarDestinations = setOf(
        R.id.homeFragment, R.id.registerFragment, R.id.loginFragment, R.id.mapsFragment,
        R.id.onboardingFragment, R.id.forgotPasswordFragment, R.id.editProfileFragment,
        R.id.editPetFragment, R.id.chatFragment, R.id.statusFragment
    )

    private val visibleBottomNavDestinations = setOf(
        R.id.homeFragment, R.id.profileFragment, R.id.promoFragment, R.id.historyFragment
    )

    private fun getTitleForDestination(destinationId: Int): String {
        return when (destinationId) {
            R.id.profileFragment -> "Profile"
            R.id.promoFragment -> "Promo"
            R.id.historyFragment -> "History"
            R.id.listPromoFragment, R.id.listArticleFragment -> "Detail Article"
            R.id.chosePetFragment, R.id.imageScanFragment, R.id.instructionScanFragment,
            R.id.resultScanFragment, R.id.tabularFragment -> "Scan"
            R.id.dokterProfileAppointmentFragment -> "Doctor Profile"
            R.id.invoiceAppointmentFragment -> "Invoice"
            R.id.consultationFragment -> "Consultation"
            R.id.paymentFragment -> "Payment"
            R.id.appointmentFragment -> "Appointment"
            else -> getString(R.string.app_name)
        }
    }
}
