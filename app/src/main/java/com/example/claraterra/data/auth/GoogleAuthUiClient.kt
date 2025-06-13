package com.example.claraterra.data.auth

import android.content.Context
import android.content.Intent
import com.example.claraterra.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUiClient(
    private val context: Context
) {
    private val auth = Firebase.auth

    // Configuración para pedir el ID Token de Google que necesita Firebase.
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(context, gso)

    // Esta función ahora devuelve el Intent para lanzar la ventana de login. ¡Mucho más simple!
    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    // Esta función recibe el resultado de la ventana de login y se autentica con Firebase.
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)!!
            val idToken = account.idToken!!
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val user = auth.signInWithCredential(credential).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: ApiException) {
            e.printStackTrace()
            SignInResult(data = null, errorMessage = "Error de Google: ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(data = null, errorMessage = e.message)
        }
    }

    suspend fun signOut() {
        try {
            googleSignInClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    // Función rápida para saber si ya hay un usuario logueado.
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}

// Estas clases de datos quedan igual.
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)