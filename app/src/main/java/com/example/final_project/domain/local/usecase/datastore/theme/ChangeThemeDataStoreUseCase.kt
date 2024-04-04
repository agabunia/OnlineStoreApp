package com.example.final_project.domain.local.usecase.datastore.theme

import android.util.Log.d
import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import com.example.final_project.domain.local.user_data_key.PreferenceKeys
import javax.inject.Inject

class ChangeThemeDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isLight: Boolean) {
        val theme = if (isLight) "light" else "dark"
        dataStoreRepository.putThemeString(key = PreferenceKeys.THEME, value = theme)
        d("homeTest", "usecase: $isLight")
    }
}