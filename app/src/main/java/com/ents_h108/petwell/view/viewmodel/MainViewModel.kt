package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.utils.Result
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _getPromo = MutableLiveData<Result<List<Article>>>()
    val promoType: LiveData<Result<List<Article>>> = _getPromo

    private val _getArticles = MutableLiveData<Result<List<Article>>>()
    val articleType: LiveData<Result<List<Article>>> = _getArticles

    fun getPromo() {
        _getPromo.value = Result.Loading
        viewModelScope.launch {
            val result = mainRepository.getArticles("promo")
            _getPromo.value = result
        }
    }

    fun getArticles() {
        _getArticles.value = Result.Loading
        viewModelScope.launch {
            val result = mainRepository.getArticles("artikel")
            _getArticles.value = result
        }
    }
}
