package com.adrencina.yoshop.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrencina.yoshop.data.local.dao.ProductoDao
import com.adrencina.yoshop.data.local.entity.Producto
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

    fun onSaveProducto(
        id: Int?,
        nombre: String,
        costoStr: String,
        ventaStr: String,
        stockStr: String,
        descripcion: String?,
        imagenUri: String?
    ) {
        val costo = costoStr.toDoubleOrNull()
        val venta = ventaStr.toDoubleOrNull()
        val stock = stockStr.toIntOrNull()

        if (nombre.isBlank() || costo == null || venta == null || stock == null) {
            return
        }

        viewModelScope.launch {
            val productoActual = _uiState.value.productoSeleccionado
            if (productoActual == null) {
                val nuevoProducto = Producto(
                    nombre = nombre,
                    precioCosto = costo,
                    precioVenta = venta,
                    stock = stock,
                    descripcion = descripcion,
                    imagenUri = imagenUri
                )
                productoDao.insertarProducto(nuevoProducto)
            } else {
                val productoActualizado = productoActual.copy(
                    nombre = nombre,
                    precioCosto = costo,
                    precioVenta = venta,
                    stock = stock,
                    descripcion = descripcion,
                    imagenUri = imagenUri
                )
                productoDao.actualizarProducto(productoActualizado)
            }
            onDismissDialog()
        }
    }
}