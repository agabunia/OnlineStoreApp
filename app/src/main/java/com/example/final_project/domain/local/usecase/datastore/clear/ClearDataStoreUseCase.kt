package com.example.final_project.domain.local.usecase.datastore.clear

import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import javax.inject.Inject

class ClearDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.clear()
}