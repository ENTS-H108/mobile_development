package com.ents_h108.petwell.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository, private val pref: UserPreferences) :
    ViewModel() {

    fun setPetActive(id: String) {
        viewModelScope.launch {
            pref.setPetActive(id)
        }
    }

    fun getContent(type: String) = mainRepository.getArticles(type).cachedIn(viewModelScope)

    fun getPets() =
        mainRepository.getPets()

    fun fetchUserProfile() = mainRepository.getProfileUser()

    fun getPet(id: String) = mainRepository.getPet(id)

    fun editPet(id: String, name: String, species: String, age: Int) =
        mainRepository.editPets(id, name, species, age)

    fun addPet(name: String, species: String, age: Int) = mainRepository.addPets(name, species, age)

    fun deletePet(id: String) = mainRepository.deletePet(id)

    fun editProfile(username: String, profilePict: String?) =
        mainRepository.editProfile(username, profilePict)

    fun addHistory(id: String, type: Int) = mainRepository.addHistory(id, type)

    fun changePassword(currPw: String, newPw: String) = mainRepository.changePassword(currPw, newPw)

    fun getAllDoctor() = mainRepository.getDoctorList()


    //    selected pet
    private val _selectedPet = MutableLiveData<String>()
    val selectedPet: LiveData<String> get() = _selectedPet
    fun setSelectedPet(pet: String) {
        _selectedPet.value = pet
    }

    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> get() = _imageUri
    fun setImageUri(uri: String) {
        _imageUri.value = uri
    }
}
