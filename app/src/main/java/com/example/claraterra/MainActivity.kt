package com.example.claraterra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.claraterra.ui.navigation.AppNavGraph
import com.example.claraterra.ui.theme.ClaraTerraTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClaraTerraTheme {
                val navController = rememberNavController()
                val isLoggedIn = auth.currentUser != null
                AppNavGraph(navController = navController, isLoggedIn = isLoggedIn)
            }
        }
    }
}