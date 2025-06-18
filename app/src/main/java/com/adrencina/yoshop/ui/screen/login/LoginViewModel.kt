package com.adrencina.yoshop.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.adrencina.yoshop.data.auth.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        Log.d("DEBUG_LOGIN", "ViewModel: onSignInResult. El resultado tiene datos? ${result.data != null}")
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }
}