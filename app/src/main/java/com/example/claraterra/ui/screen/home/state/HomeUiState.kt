package com.example.claraterra.ui.screen.home.state

data class HomeUiState(
    val gananciaNetaDiaria: Double = 0.0,
    val ingresoBrutoSemanal: Double = 0.0,
    val metaVentaSemanal: Double = 50000.0,
    val nombreUsuario: String = "Clara",
    val isLoading: Boolean = true
)