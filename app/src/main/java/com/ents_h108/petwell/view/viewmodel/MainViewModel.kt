package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.ViewModel
import com.ents_h108.petwell.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    fun getPromo() =
        mainRepository.getArticles("promo")

    fun getArticles() =
        mainRepository.getArticles("artikel")

    fun getPets() =
        mainRepository.getPets()

    fun fetchUserProfile() = mainRepository.getProfileUser()

    fun editPet(id: String, name: String, species: String) = mainRepository.editPets(id, name, species)

    fun addPet(name: String, species: String) = mainRepository.addPets(name, species)

    fun deletePet(id: String) = mainRepository.deletePet(id)

    fun editProfile(username: String, profilepict: String) = mainRepository.editProfile(username, profilepict)
}
