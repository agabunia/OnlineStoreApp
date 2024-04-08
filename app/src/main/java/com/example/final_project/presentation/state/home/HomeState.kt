package com.example.final_project.presentation.state.home

import com.example.final_project.presentation.model.home.HomeMainModel

data class HomeState(
    val dataList: HomeMainModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
