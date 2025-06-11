package com.example.claraterra.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claraterra.data.repository.RegistroRepository
import com.example.claraterra.ui.screen.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RegistroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observarDatosFinancieros()
    }

    private fun observarDatosFinancieros() {
        viewModelScope.launch {
            // --- Calcular rangos de fechas ---
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0); calendar.set(Calendar.MINUTE, 0);
            val inicioHoy = calendar.timeInMillis
            calendar.set(Calendar.HOUR_OF_DAY, 23); calendar.set(Calendar.MINUTE, 59);
            val finHoy = calendar.timeInMillis

            calendar.time = Calendar.getInstance().time
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            val inicioSemana = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_WEEK, 6)
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            val finSemana = calendar.timeInMillis

            // --- Obtener los flujos de datos ---
            val gananciaDiariaFlow = repository.obtenerGananciaNetaEnRango(inicioHoy, finHoy)
            val ingresoSemanalFlow = repository.obtenerIngresoBrutoEnRango(inicioSemana, finSemana)

            // --- Combinar los flujos ---
            combine(gananciaDiariaFlow, ingresoSemanalFlow) { gananciaDia, ingresoSemana ->
                _uiState.value.copy(
                    gananciaNetaDiaria = gananciaDia,
                    ingresoBrutoSemanal = ingresoSemana,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
}