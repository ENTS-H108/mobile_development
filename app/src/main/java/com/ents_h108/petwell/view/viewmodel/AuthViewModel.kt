package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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


    fun getLoginStatus(): LiveData<Boolean?> {
        return pref.getLoginStatus().asLiveData()
    }
    fun getToken(): LiveData<String?> {
        return pref.getToken().asLiveData()
    }

    fun resetPassword(newPassword: String, token: String) =
        authRepository.resetPassword(newPassword, token)

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getUsername(): LiveData<String?> {
        return pref.getUsername().asLiveData()
    }

    fun saveLoginStatus(token: String, username: String, email: String) {
        viewModelScope.launch {
            pref.saveToken(token, username, email)
        }
    }
}
