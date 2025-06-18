package com.adrencina.yoshop.ui.screen.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val auth = Firebase.auth
    private val weeklyGoalKey = stringPreferencesKey("weekly_goal")
    private val isDarkThemeKey = booleanPreferencesKey("is_dark_theme")

    val weeklyGoalFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[weeklyGoalKey] ?: "50000"
    }

    val isDarkThemeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[isDarkThemeKey] ?: false
    }

    suspend fun setWeeklyGoal(goal: String) {
        context.dataStore.edit { settings ->
            settings[weeklyGoalKey] = goal
        }
    }

    suspend fun setTheme(isDark: Boolean) {
        context.dataStore.edit { settings ->
            settings[isDarkThemeKey] = isDark
        }
    }

    fun signOut() {
        auth.signOut()
    }
}