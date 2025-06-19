package com.adrencina.yoshop.ui.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrencina.yoshop.data.local.dao.ProductoDao
import com.adrencina.yoshop.data.local.dao.VentaDao
import com.adrencina.yoshop.data.local.entity.Producto
import com.adrencina.yoshop.data.local.entity.Venta
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
        val producto = _uiState.value.productoSeleccionado ?: return
        if (cantidad in 1..producto.stock) {
            _uiState.update { it.copy(cantidadVenta = cantidad) }
        }
    }

    fun onConfirmarVenta() {
        viewModelScope.launch {
            val productoAVender = uiState.value.productoSeleccionado
            val cantidadVendida = uiState.value.cantidadVenta

            if (productoAVender == null || cantidadVendida <= 0 || cantidadVendida > productoAVender.stock) {
                onDismissDialog()
                return@launch
            }

            val nuevaVenta = Venta(
                productoId = productoAVender.id,
                cantidad = cantidadVendida,
                precioVentaAlMomento = productoAVender.precioVenta
            )

            val productoActualizado = productoAVender.copy(
                stock = productoAVender.stock - cantidadVendida
            )

            ventaDao.registrarVentaYActualizarStock(nuevaVenta, productoActualizado)

            onDismissDialog()
        }
    }
}