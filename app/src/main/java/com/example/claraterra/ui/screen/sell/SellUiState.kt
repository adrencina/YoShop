package com.example.claraterra.ui.screen.sell

import com.example.claraterra.data.local.entity.Producto

data class SellUiState(
    val searchQuery: String = "",
    val productos: List<Producto> = emptyList(),
    val productoSeleccionado: Producto? = null,
    val cantidadVenta: Int = 1,
    val isLoading: Boolean = true
)