package com.adrencina.yoshop.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.adrencina.yoshop.ui.common.GreetingTopBar
import com.adrencina.yoshop.ui.common.ScreenContainer
import com.adrencina.yoshop.ui.home.GraphSection
import com.adrencina.yoshop.ui.home.MotivationalSection
import com.adrencina.yoshop.ui.home.StockSection
import com.adrencina.yoshop.viewmodel.HomeViewModel

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