package com.example.claraterra.ui.screen.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claraterra.data.local.dao.ProductoDao
import com.example.claraterra.data.local.dao.VentaDao
import com.example.claraterra.data.local.entity.Producto
import com.example.claraterra.data.local.entity.Venta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val productoDao: ProductoDao,
    private val ventaDao: VentaDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(SellUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val productosFiltrados: StateFlow<List<Producto>> = _searchQuery
        .flatMapLatest { query ->
            productoDao.buscarProductos(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onProductoSeleccionado(producto: Producto) {
        _uiState.update { it.copy(productoSeleccionado = producto, cantidadVenta = 1) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(productoSeleccionado = null) }
    }

    fun onCantidadChange(cantidad: Int) {
        if (cantidad > 0) {
            _uiState.update { it.copy(cantidadVenta = cantidad) }
        }
    }

    fun onConfirmarVenta() {
        viewModelScope.launch {
            val estadoActual = _uiState.value
            estadoActual.productoSeleccionado?.let { producto ->
                val nuevaVenta = Venta(
                    productoId = producto.id,
                    cantidad = estadoActual.cantidadVenta,
                    precioVentaAlMomento = producto.precioVenta
                )
                ventaDao.insertarVenta(nuevaVenta)
                onDismissDialog()
            }
        }
    }
}