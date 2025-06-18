package com.example.claraterra.ui.screen.balance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claraterra.data.repository.RegistroRepository
import com.example.claraterra.ui.screen.balance.state.BalanceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.util.Calendar
import javax.inject.Inject

enum class Periodo { DIA, SEMANA, MES }

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class BalanceViewModel @Inject constructor(
    private val repository: RegistroRepository
) : ViewModel() {

    private val _periodoSeleccionado = MutableStateFlow(Periodo.DIA)

    val uiState: StateFlow<BalanceUiState> = _periodoSeleccionado.flatMapLatest { periodo ->
        val (inicio, fin) = getFechasParaPeriodo(periodo)

        // **NOTA:** Asegurate de que tu RegistroRepository tenga estas funciones!
        combine(
            repository.obtenerIngresoBrutoEnRango(inicio, fin),
            repository.obtenerTotalGastosEnRango(inicio, fin), // Pide la suma de gastos
            repository.obtenerVentasDetalladasEnRango(inicio, fin), // Pide la lista de ventas
            repository.obtenerGastosDetalladosEnRango(inicio, fin)  // Pide la lista de gastos
        ) { ingresos, gastos, ventas, gastosDetallados ->
            val ganancia = (ingresos ?: 0.0) - (gastos ?: 0.0)
            BalanceUiState(
                periodo = periodo,
                totalIngresos = ingresos ?: 0.0,
                totalGastos = gastos ?: 0.0,
                gananciaNeta = ganancia,
                ultimasVentas = ventas,
                ultimosGastos = gastosDetallados,
                isLoading = false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BalanceUiState()
    )

    fun cambiarPeriodo(nuevoPeriodo: Periodo) {
        _periodoSeleccionado.value = nuevoPeriodo
    }

    private fun getFechasParaPeriodo(periodo: Periodo): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        val fin = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        when (periodo) {
            Periodo.DIA -> { /* ya está seteado a hoy a las 00:00 */ }
            Periodo.SEMANA -> calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            Periodo.MES -> calendar.set(Calendar.DAY_OF_MONTH, 1)
        }
        val inicio = calendar.timeInMillis
        return Pair(inicio, fin)
    }
}