package com.ents_h108.petwell.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    private val authRepository: AuthRepository by lazy {
        AuthRepository()
    }
    private val mainRepository : MainRepository by lazy {
        MainRepository()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mainRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
