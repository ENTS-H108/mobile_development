package com.ents_h108.petwell.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

val repositoryModule = module {
    single { UserPreferences.getInstance(androidContext().dataStore) }
    single { MainRepository(get()) }
    single { AuthRepository() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
}

val appModule = listOf(repositoryModule, viewModelModule)
