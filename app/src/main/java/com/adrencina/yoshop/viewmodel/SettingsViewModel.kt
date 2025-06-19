package com.adrencina.yoshop.ui.settings

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

    val uiState: StateFlow<SettingsUiState> =
        combine(
            settingsRepository.weeklyGoalFlow,
            settingsRepository.isDarkThemeFlow
        ) { goal, isDark ->
            SettingsUiState(weeklyGoal = goal, isDarkTheme = isDark)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState()
        )

    private val _signOutEvent = MutableSharedFlow<Unit>()
    val signOutEvent = _signOutEvent.asSharedFlow()

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

    fun onSignOutClicked() {
        viewModelScope.launch {
            settingsRepository.signOut()
            _signOutEvent.emit(Unit)
        }
    }
}