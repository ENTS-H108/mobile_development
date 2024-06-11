package com.ents_h108.petwell.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.ActivityMainBinding
import com.ents_h108.petwell.utils.Utils.getAddressFromLocation
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentCity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        authViewModel.getUsername().observe(this) {
            binding.profileUserName.text = it.toString()
        }

        navController.addOnDestinationChangedListener { _, nd, _ ->
            binding.apply {
                when (nd.id) {
                    R.id.registerFragment, R.id.loginFragment, R.id.mapsFragment, R.id.onboardingFragment, R.id.forgotPasswordFragment, R.id.editProfileFragment, R.id.editPetFragment, R.id.chatFragment -> {
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
                    // Fitur Scan
                    R.id.chosePetFragment, R.id.imageScanFragment, R.id.instructionScanFragment, R.id.resultScanFragment, R.id.tabularFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.GONE
                        userDetail.visibility = View.GONE
                        locationDetail.visibility = View.GONE
                        topAppBar.title = "Fitur Scan"
                    }
                    // Fitur Appointment
                    R.id.appointmentFragment, R.id.detailAppointment -> {
                        topAppBar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.GONE
                        userDetail.visibility = View.VISIBLE
                        locationDetail.visibility = View.VISIBLE
                        topAppBar.title = null
                    }
                    // Fitur Chat
                    R.id.consultationFragment, R.id.paymentFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.GONE
                        userDetail.visibility = View.GONE
                        locationDetail.visibility = View.GONE
                        topAppBar.title = when (nd.id) {
                            R.id.consultationFragment -> "Consultation"
                            R.id.paymentFragment -> "Payment"
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

        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                binding.locationTitle.text = getString(R.string.permission_denied)
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                getAddressFromLocation(this, location.latitude, location.longitude) { address, _, _ ->
                    if (address != null) {
                        binding.locationTitle.text = address
                    } else {
                        binding.locationTitle.text = getString(R.string.city_not_found)
                    }
                }
            }
        }.addOnFailureListener {
            binding.locationTitle.text = getString(R.string.city_not_found)
        }
    }

    private fun btmNavTrue(destinationId: Int) {
        when (destinationId) {
            R.id.homeFragment -> binding.bottomNavigation.menu.findItem(R.id.home).isChecked = true
            R.id.historyFragment -> binding.bottomNavigation.menu.findItem(R.id.history).isChecked = true
            R.id.promoFragment -> binding.bottomNavigation.menu.findItem(R.id.promo).isChecked = true
            R.id.profileFragment -> binding.bottomNavigation.menu.findItem(R.id.profile).isChecked = true
        }
    }
}
