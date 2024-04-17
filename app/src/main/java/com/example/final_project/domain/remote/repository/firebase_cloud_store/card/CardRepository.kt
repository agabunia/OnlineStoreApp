package com.example.final_project.domain.remote.repository.firebase_cloud_store.card

import com.example.final_project.data.common.Resource
import com.example.final_project.domain.remote.model.firebase_cloud_store.card.GetCard
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun addCard(uid: String, card: GetCard): Flow<Resource<Boolean>>
    suspend fun deleteCard(uid: String, id: String): Flow<Resource<Boolean>>
    suspend fun getAllCards(uid: String): Flow<Resource<List<GetCard>>>
}
