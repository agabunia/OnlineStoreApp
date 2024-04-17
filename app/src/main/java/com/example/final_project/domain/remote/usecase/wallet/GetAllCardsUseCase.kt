package com.example.final_project.domain.remote.usecase.wallet

import com.example.final_project.domain.remote.repository.firebase_cloud_store.card.CardRepository
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(uid: String) = cardRepository.getAllCards(uid = uid)
}