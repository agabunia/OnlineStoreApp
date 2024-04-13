package com.example.final_project.domain.local.user_data_key

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val TOKEN = stringPreferencesKey("access_token")
    val THEME = stringPreferencesKey("THEME_KEYS")
    val LANGUAGE = stringPreferencesKey("LANGUAGE_KEYS")
    val UID_KEY = stringPreferencesKey("USER_UID_KEY")
}