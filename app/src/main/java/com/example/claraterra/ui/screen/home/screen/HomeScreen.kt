package com.example.claraterra.ui.screen.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.screen.home.sections.GraphSection
import com.example.claraterra.ui.screen.home.sections.MotivationalSection
import com.example.claraterra.ui.screen.home.sections.StockSection
import com.example.claraterra.ui.component.GreetingTopBar

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    userName: String = "Clara" // agregaremos info de viewmodel
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { GreetingTopBar(userName = userName) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Sección del gráfico
            GraphSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de frase motivacional
            MotivationalSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de stock
            StockSection()
        }
    }
}