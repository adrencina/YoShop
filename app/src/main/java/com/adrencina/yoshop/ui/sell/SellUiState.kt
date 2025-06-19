package com.adrencina.yoshop.ui.sell

import com.adrencina.yoshop.data.local.entity.Producto

data class SellUiState(
    val searchQuery: String = "",
    val productos: List<Producto> = emptyList(),
    val productoSeleccionado: Producto? = null,
    val cantidadVenta: Int = 1,
    val isLoading: Boolean = true
)