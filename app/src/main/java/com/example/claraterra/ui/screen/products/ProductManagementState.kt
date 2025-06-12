package com.example.claraterra.ui.screen.products

import com.example.claraterra.data.local.entity.Producto

data class ProductManagementState(
    val productos: List<Producto> = emptyList(),
    val isDialogVisible: Boolean = false,
    val productoSeleccionado: Producto? = null
)