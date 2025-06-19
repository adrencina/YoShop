package com.adrencina.yoshop.ui.home

data class HomeUiState(
    val gananciaNetaDiaria: Double = 0.0,
    val ingresoBrutoSemanal: Double = 0.0,
    val metaVentaSemanal: Double = 50000.0,
    val nombreUsuario: String = "Clara",
    val isLoading: Boolean = true,

    // --- NUEVOS CAMPOS DE ESTADO DE STOCK ---
    val stockStatus: StockStatus = StockStatus.OK,
    val itemsConStockCritico: Int = 1
)