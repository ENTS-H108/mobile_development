package com.ents_h108.petwell.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ents_h108.petwell.BuildConfig
import com.ents_h108.petwell.data.model.chatbot.ChatViewModel
import com.ents_h108.petwell.data.remote.ApiService
import com.ents_h108.petwell.data.repository.AuthRepository
import com.ents_h108.petwell.data.repository.MainRepository
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        val hostname = "petwell-api-wmaq4jxv4a-et.a.run.app"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/8q4+QQFGqeoyIS6kiFtsMr3+uU+fZT1dptrP7AJ/YGU=")
            .add(hostname, "sha256/zCTnfLwLKbS9S2sbp+uFz4KZOocFvXxkV06Ce9O5M2w=")
            .add(hostname, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { UserPreferences.getInstance(androidContext().dataStore) }
    single { MainRepository(get(), get()) }
    single { AuthRepository(get()) }
}

val credentialManagerModule = module {
    single { CredentialManager.create(androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { ChatViewModel() }
}

val appModule = listOf(networkModule, repositoryModule, viewModelModule, credentialManagerModule)
