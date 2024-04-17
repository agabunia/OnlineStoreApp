package com.example.final_project.domain.remote.usecase.wallet

import com.example.final_project.domain.remote.repository.firebase_cloud_store.card.CardRepository
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(uid: String, cardId: String) = cardRepository.deleteCard(uid = uid, id = cardId)
}