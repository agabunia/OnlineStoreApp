package com.example.final_project.domain.remote.usecase.wallet

import com.example.final_project.domain.remote.model.firebase_cloud_store.card.GetCard
import com.example.final_project.domain.remote.repository.firebase_cloud_store.card.CardRepository
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(uid: String, card: GetCard) = cardRepository.addCard(uid = uid, card = card)
}