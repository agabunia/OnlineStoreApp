package com.example.final_project.domain.remote.usecase.home

import com.example.final_project.domain.remote.repository.home.CategoryListRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val categoryListRepository: CategoryListRepository
) {
    suspend operator fun invoke() = categoryListRepository.getCategoryList()
}