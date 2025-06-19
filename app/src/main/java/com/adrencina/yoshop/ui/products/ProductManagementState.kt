package com.adrencina.yoshop.ui.products

import com.adrencina.yoshop.data.local.entity.Producto

data class ProductManagementState(
    val productos: List<Producto> = emptyList(),
    val isDialogVisible: Boolean = false,
    val productoSeleccionado: Producto? = null
)