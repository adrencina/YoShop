package com.adrencina.yoshop.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val auth = Firebase.auth
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun signIn(email: String, pass: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }
            try {
                auth.signInWithEmailAndPassword(email, pass).await()
                _authState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _authState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun signUp(email: String, pass: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }
            try {
                auth.createUserWithEmailAndPassword(email, pass).await()
                _authState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _authState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState()
    }
}