package com.adrencina.yoshop.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrencina.yoshop.ui.theme.TerraBackground
import com.adrencina.yoshop.ui.theme.TerraPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingTopBar(userName: String) {
    TopAppBar(
        title = {
            Text(
                text = "¡Hola, $userName!",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = "User Avatar",
                modifier = Modifier.padding(start = 12.dp),
                tint = TerraPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TerraBackground,
            titleContentColor = TerraPrimary
        )
    )
}