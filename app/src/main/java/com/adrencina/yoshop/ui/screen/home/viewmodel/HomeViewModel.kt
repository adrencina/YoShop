package com.adrencina.yoshop.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrencina.yoshop.data.local.dao.ProductoDao
import com.adrencina.yoshop.data.repository.RegistroRepository
import com.adrencina.yoshop.ui.screen.home.state.HomeUiState
import com.adrencina.yoshop.ui.screen.home.state.StockStatus
import com.adrencina.yoshop.ui.screen.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RegistroRepository,
    private val productoDao: ProductoDao,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        repository.obtenerGananciaNetaEnRango(getInicioHoy(), Long.MAX_VALUE),
        repository.obtenerIngresoBrutoEnRango(getInicioSemana(), Long.MAX_VALUE),
        productoDao.obtenerProductos(),
        settingsRepository.weeklyGoalFlow
    ) { gananciaDia, ingresoSemana, productos, metaSemanal ->

        val agotados = productos.count { it.stock == 0 }
        val stockBajo = productos.count { it.stock in 1..5 }

        val status = when {
            productos.isEmpty() -> StockStatus.EMPTY
            agotados > 0 -> StockStatus.CRITICAL
            stockBajo > 0 -> StockStatus.LOW
            else -> StockStatus.OK
        }

        HomeUiState(
            gananciaNetaDiaria = gananciaDia ?: 0.0,
            ingresoBrutoSemanal = ingresoSemana ?: 0.0,
            metaVentaSemanal = metaSemanal.toDoubleOrNull() ?: 50000.0,
            stockStatus = status,
            itemsConStockCritico = agotados + stockBajo,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isLoading = true)
    )

    private fun getInicioHoy(): Long {
        return Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0) }.timeInMillis
    }
    private fun getInicioSemana(): Long {
        return Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY; set(Calendar.DAY_OF_WEEK, firstDayOfWeek); set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0) }.timeInMillis
    }
}