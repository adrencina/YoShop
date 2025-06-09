package com.example.claraterra.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingTopBar(userName: String) {
    TopAppBar(
        title = { Text(text = "¡Hola, $userName!") },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }
    )
}