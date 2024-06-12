package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository, private val pref: UserPreferences) : ViewModel() {

    fun login(emailInput: String, passwordInput: String) =
        authRepository.loginUser(emailInput, passwordInput)

    fun register(name: String, email: String, password: String) =
        authRepository.registerUser(name, email, password)

    fun requestToken(email: String) =
        authRepository.requestToken(email)

    fun resetPassword(newPassword: String, token: String) =
        authRepository.resetPassword(newPassword, token)

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun saveLoginStatus(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }
}
