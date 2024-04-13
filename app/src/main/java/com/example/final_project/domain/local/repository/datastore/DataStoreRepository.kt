package com.example.final_project.domain.local.repository.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveString(key: Preferences.Key<String>, value: String)

    fun readString(key: Preferences.Key<String>): Flow<String>

    suspend fun clear()

    // For Theme change
    suspend fun putThemeString(key: Preferences.Key<String>, value: String)
    suspend fun getThemeString(key: Preferences.Key<String>): Flow<String>

    // For language change
    suspend fun putLanguageString(key: Preferences.Key<String>, value: String)
    suspend fun getLanguageString(key: Preferences.Key<String>): Flow<String>

    // For profile Picture
    fun readUid(key: Preferences.Key<String>): Flow<String>
    suspend fun saveUdi(key: Preferences.Key<String>, value: String)

}