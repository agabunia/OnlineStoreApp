package com.example.final_project.domain.local.usecase.datastore.profile_image

import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import com.example.final_project.domain.local.user_data_key.PreferenceKeys
import javax.inject.Inject

class ReadUserUidUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readUid(PreferenceKeys.UID_KEY)
}