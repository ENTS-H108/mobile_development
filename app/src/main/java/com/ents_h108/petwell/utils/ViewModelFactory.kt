package com.ents_h108.petwell.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.ui.viewmodel.AuthViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    private val authRepository: AuthRepository by lazy {
        AuthRepository()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
