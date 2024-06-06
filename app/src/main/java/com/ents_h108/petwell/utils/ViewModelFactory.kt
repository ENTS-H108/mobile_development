package com.ents_h108.petwell.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel

class ViewModelFactory(private val pref: UserPreferences) : ViewModelProvider.NewInstanceFactory() {

    private val authRepository: AuthRepository by lazy {
        AuthRepository()
    }

    private val mainRepository : MainRepository by lazy {
        MainRepository()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository, pref) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java))  {
            return MainViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
