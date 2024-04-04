package com.example.final_project.domain.local.usecase.datastore.authorization

import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import com.example.final_project.domain.local.user_data_key.PreferenceKeys
import javax.inject.Inject

class SaveDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(token: String) {
        dataStoreRepository.saveString(key = PreferenceKeys.TOKEN, value = token)
    }
}