package com.example.final_project.domain.local.usecase.datastore.theme

import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import com.example.final_project.domain.local.user_data_key.PreferenceKeys
import javax.inject.Inject

class GetThemeDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.getThemeString(key = PreferenceKeys.THEME)
}