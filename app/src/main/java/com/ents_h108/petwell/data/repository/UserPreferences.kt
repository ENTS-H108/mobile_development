package com.ents_h108.petwell.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val key = stringPreferencesKey("token")
    private val state = booleanPreferencesKey("state")
    private val petActive = stringPreferencesKey("pet")

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[state] = false
            preferences[key] = ""
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[state] = true
            preferences[key] = token
        }
    }

    suspend fun setPetActive(id: String) {
        dataStore.edit { preferences ->
            preferences[petActive] = id
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    fun getLoginStatus(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[state]
        }
    }

    fun getPetActive(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[petActive]
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}