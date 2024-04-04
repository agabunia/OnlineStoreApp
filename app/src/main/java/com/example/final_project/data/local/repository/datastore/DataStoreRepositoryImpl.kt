package com.example.final_project.data.local.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreRepository {
    override suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }

    override fun readString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map {
            it[key] ?: ""
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun putThemeString(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getThemeString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map {
            it[key] ?: "light"
        }
    }

    override suspend fun putLanguageString(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getLanguageString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map {
            it[key] ?: "en"
        }
    }


}
