package com.example.claraterra.data.repository

import com.example.claraterra.data.local.dao.GastoDao
import com.example.claraterra.data.local.dao.VentaDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistroRepository @Inject constructor(
    private val ventaDao: VentaDao,
    private val gastoDao: GastoDao
) {
    // Expone el flujo de GANANCIA NETA
    fun obtenerGananciaNetaEnRango(startTime: Long, endTime: Long): Flow<Double> {
        return ventaDao.getGananciaNetaEnRango(startTime, endTime)
    }

    // Expone el flujo de INGRESO BRUTO (para la meta semanal)
    fun obtenerIngresoBrutoEnRango(startTime: Long, endTime: Long): Flow<Double> {
        return ventaDao.getIngresoBrutoEnRango(startTime, endTime)
    }

    // Mantenemos los gastos por separado
    fun obtenerTotalGastosEnRango(startTime: Long, endTime: Long): Flow<Double> {
        return gastoDao.getTotalGastosEnRango(startTime, endTime)
    }
}