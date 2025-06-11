package com.example.claraterra.ui.screen.home.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.component.GreetingTopBar
import com.example.claraterra.ui.component.ScreenContainer
import com.example.claraterra.ui.screen.home.sections.GraphSection
import com.example.claraterra.ui.screen.home.sections.MotivationalSection
import com.example.claraterra.ui.screen.home.sections.StockSection
import com.example.claraterra.ui.screen.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { GreetingTopBar(userName = uiState.nombreUsuario) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        ScreenContainer(modifier = Modifier.padding(innerPadding)) {
            GraphSection(
                navController = navController,
                uiState = uiState
            )
            Spacer(modifier = Modifier.height(16.dp))
            MotivationalSection()
            Spacer(modifier = Modifier.height(16.dp))
            StockSection(navController = navController)
        }
    }
}