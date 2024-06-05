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
    private val _article = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>> = _article


    fun getArticle(): LiveData<Result<List<Article>>> {
        viewModelScope.launch {
            _article.value = Result.Loading
            val articlesresult = mainRepository.getArticles()
            _article.value = articlesresult
        }
        return articles
    }
}