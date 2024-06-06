package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.ResetPasswordResponse
import com.ents_h108.petwell.data.model.SignUpResponse
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository, private val pref: UserPreferences) : ViewModel() {

    private val _resetPasswordResult = MutableLiveData<Result<ResetPasswordResponse>>()
    val resetPasswordResult: LiveData<Result<ResetPasswordResponse>> = _resetPasswordResult

    fun login(emailInput: String, passwordInput: String) =
        authRepository.loginUser(emailInput, passwordInput)

    fun register(name: String, email: String, password: String) =
        authRepository.registerUser(name, email, password)

    fun requestToken(email: String) {
        viewModelScope.launch {
            _resetPasswordResult.value = Result.Loading
            val result = authRepository.requestToken(email)
            _resetPasswordResult.value = result
        }
    }

    fun resetPassword(token: String, newPassword: String) {
        _resetPasswordResult.value = Result.Loading
        viewModelScope.launch {
            val result = authRepository.resetPassword(token, newPassword)
            _resetPasswordResult.value = result
        }
    }

    fun getLoginStatus(): LiveData<Boolean?> {
        return pref.getLoginStatus().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun saveLoginStatus() {
        viewModelScope.launch {
            pref.saveToken()
        }
    }
}
