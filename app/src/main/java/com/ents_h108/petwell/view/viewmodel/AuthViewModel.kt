package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ents_h108.petwell.data.model.LoginResponse
import com.ents_h108.petwell.data.model.ResetPasswordResponse
import com.ents_h108.petwell.data.model.SignUpResponse
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _registerResult = MutableLiveData<Result<SignUpResponse>>()
    val registerResult: LiveData<Result<SignUpResponse>> = _registerResult

    private val _resetPasswordResult = MutableLiveData<Result<ResetPasswordResponse>>()
    val resetPasswordResult: LiveData<Result<ResetPasswordResponse>> = _resetPasswordResult

    fun login(email: String, password: String) {
        _loginResult.value = Result.Loading
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            _loginResult.value = result
        }
    }

    fun register(email: String, username: String, password: String) {
        _registerResult.value = Result.Loading
        viewModelScope.launch {
            val result = authRepository.register(email, username, password)
            _registerResult.value = result
        }
    }

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

}
