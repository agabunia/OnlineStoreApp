package com.example.final_project.domain.local.usecase.datastore.language

import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import com.example.final_project.domain.local.user_data_key.PreferenceKeys
import javax.inject.Inject

class ChangeLanguageDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isGeorgian: Boolean) {
        val language = if (isGeorgian) "ka" else "en"
        dataStoreRepository.putLanguageString(key = PreferenceKeys.LANGUAGE, value = language)
    }
}