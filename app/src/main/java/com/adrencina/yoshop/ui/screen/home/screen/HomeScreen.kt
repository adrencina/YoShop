package com.adrencina.yoshop.ui.screen.home.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.adrencina.yoshop.ui.component.GreetingTopBar
import com.adrencina.yoshop.ui.component.ScreenContainer
import com.adrencina.yoshop.ui.screen.home.sections.GraphSection
import com.adrencina.yoshop.ui.screen.home.sections.MotivationalSection
import com.adrencina.yoshop.ui.screen.home.sections.StockSection
import com.adrencina.yoshop.ui.screen.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // La HomeScreen ahora es una Columna simple que organiza su contenido.
    // La estructura principal con Scaffold y BottomBar ya la provee un componente padre.
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // La barra de saludo ahora es el primer elemento de la columna.
        GreetingTopBar(userName = uiState.nombreUsuario)

        // ScreenContainer se encarga del padding y de organizar el resto.
        ScreenContainer {
            GraphSection(
                navController = navController,
                uiState = uiState
            )
            Spacer(modifier = Modifier.height(16.dp))
            MotivationalSection()
            Spacer(modifier = Modifier.height(16.dp))
            StockSection(
                navController = navController,
                uiState = uiState
            )
        }
    }
}