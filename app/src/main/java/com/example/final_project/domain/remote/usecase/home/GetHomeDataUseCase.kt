package com.example.final_project.domain.remote.usecase.home

import com.example.final_project.domain.remote.repository.home.HomeDataRepository
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val homeDataRepository: HomeDataRepository
) {
    suspend operator fun invoke() = homeDataRepository.getHomeData()
}