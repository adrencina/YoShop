package com.example.claraterra.data.repository

import com.example.claraterra.data.local.dao.GastoDao
import com.example.claraterra.data.local.dao.VentaDao
import com.example.claraterra.data.local.entity.Gasto
import com.example.claraterra.data.local.entity.Venta
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

    // Expone el flujo de INGRESO BRUTO
    fun obtenerIngresoBrutoEnRango(startTime: Long, endTime: Long): Flow<Double> {
        return ventaDao.getIngresoBrutoEnRango(startTime, endTime)
    }

    // Expone el flujo de GASTOS TOTALES
    fun obtenerTotalGastosEnRango(startTime: Long, endTime: Long): Flow<Double> {
        return gastoDao.getTotalGastosEnRango(startTime, endTime)
    }

    // --- NUEVA FUNCIÓN PARA LA LISTA DE VENTAS ---
    fun obtenerVentasDetalladasEnRango(startTime: Long, endTime: Long): Flow<List<Venta>> {
        return ventaDao.obtenerVentasDetalladasEnRango(startTime, endTime)
    }

    // --- NUEVA FUNCIÓN PARA LA LISTA DE GASTOS ---
    fun obtenerGastosDetalladosEnRango(startTime: Long, endTime: Long): Flow<List<Gasto>> {
        return gastoDao.obtenerGastosDetalladosEnRango(startTime, endTime)
    }
}