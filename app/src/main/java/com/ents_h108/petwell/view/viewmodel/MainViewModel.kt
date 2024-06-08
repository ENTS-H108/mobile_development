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
    fun getPromo() =
        mainRepository.getArticles("promo")

    fun getArticles() =
        mainRepository.getArticles("artikel")

    fun getPets() =
        mainRepository.getPets()
}
