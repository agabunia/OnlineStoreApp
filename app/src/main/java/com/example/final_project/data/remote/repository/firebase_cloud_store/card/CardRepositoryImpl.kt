package com.example.final_project.data.remote.repository.firebase_cloud_store.card

import com.example.final_project.data.common.Resource
import com.example.final_project.data.common.await
import com.example.final_project.data.remote.mapper.firebase_cloud_store.card.toData
import com.example.final_project.data.remote.mapper.firebase_cloud_store.card.toDomain
import com.example.final_project.data.remote.model.firebase_cloud_store.card.CardDto
import com.example.final_project.domain.remote.model.firebase_cloud_store.card.GetCard
import com.example.final_project.domain.remote.repository.firebase_cloud_store.card.CardRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CardRepository {
    override suspend fun addCard(uid: String, card: GetCard): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val cardDataModel = card.toData()
                db.collection("users").document(uid).collection("cards").add(cardDataModel).await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.message.toString()))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun deleteCard(uid: String, id: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                db.collection("users").document(uid).collection("cards").document(id).delete()
                    .await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.message.toString()))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllCards(uid: String): Flow<Resource<List<GetCard>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val getCollection =
                    db.collection("users").document(uid).collection("cards").get().await()
                val cards = getCollection.documents.mapNotNull { documents ->
                    documents.toObject(CardDto::class.java)?.toDomain()
                }
                emit(Resource.Success(cards))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.message.toString()))
            }
            emit(Resource.Loading(false))
        }
    }

}