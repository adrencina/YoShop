package com.example.claraterra.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.weeklyGoalFlow,
                settingsRepository.isDarkThemeFlow
            ) { goal, isDark ->
                SettingsUiState(weeklyGoal = goal, isDarkTheme = isDark)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun onGoalChanged(newGoal: String) {
        viewModelScope.launch {
            settingsRepository.setWeeklyGoal(newGoal)
        }
    }

    fun onThemeChanged(isDark: Boolean) {
        viewModelScope.launch {
            settingsRepository.setTheme(isDark)
        }
    }
}