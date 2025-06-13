package com.example.claraterra.ui.screen.balance.state

import com.example.claraterra.data.local.entity.Gasto
import com.example.claraterra.data.local.entity.Venta
import com.example.claraterra.ui.screen.balance.viewmodel.Periodo

data class BalanceUiState(
    val periodo: Periodo = Periodo.DIA,
    val totalIngresos: Double = 0.0,
    val totalGastos: Double = 0.0,
    val gananciaNeta: Double = 0.0,
    val ultimasVentas: List<Venta> = emptyList(),
    val ultimosGastos: List<Gasto> = emptyList(),
    val isLoading: Boolean = true
)