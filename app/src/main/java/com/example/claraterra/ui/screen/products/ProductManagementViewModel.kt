package com.example.claraterra.ui.screen.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claraterra.data.local.dao.ProductoDao
import com.example.claraterra.data.local.entity.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductManagementViewModel @Inject constructor(
    private val productoDao: ProductoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductManagementState())
    val uiState = _uiState.asStateFlow()

    val productos: StateFlow<List<Producto>> = productoDao.obtenerProductos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onAddProductoClick() {
        _uiState.update { it.copy(isDialogVisible = true, productoSeleccionado = null) }
    }

    fun onEditProductoClick(producto: Producto) {
        _uiState.update { it.copy(isDialogVisible = true, productoSeleccionado = producto) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(isDialogVisible = false, productoSeleccionado = null) }
    }

    fun onDeleteProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.eliminarProducto(producto)
        }
    }

    fun onSaveProducto(nombre: String, costoStr: String, ventaStr: String) {
        // Validación básica
        val costo = costoStr.toDoubleOrNull()
        val venta = ventaStr.toDoubleOrNull()
        if (nombre.isBlank() || costo == null || venta == null) {
            return
        }

        viewModelScope.launch {
            val productoActual = _uiState.value.productoSeleccionado
            if (productoActual == null) {
                productoDao.insertarProducto(
                    Producto(nombre = nombre, precioCosto = costo, precioVenta = venta)
                )
            } else {
                productoDao.actualizarProducto(
                    productoActual.copy(nombre = nombre, precioCosto = costo, precioVenta = venta)
                )
            }
            onDismissDialog()
        }
    }
}
