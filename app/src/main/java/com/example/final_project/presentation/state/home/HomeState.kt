package com.example.final_project.presentation.state.home

import com.example.final_project.presentation.model.home.HomeWrapperModel

data class HomeState(
    val dataList: List<HomeWrapperModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
