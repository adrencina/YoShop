package com.example.claraterra.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.claraterra.data.local.entity.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Insert
    suspend fun insertarVenta(venta: Venta)

    // CONSULTA GANANCIA
    @Query("""
        SELECT COALESCE(SUM(v.cantidad * (v.precioVentaAlMomento - p.precioCosto)), 0.0)
        FROM ventas AS v
        INNER JOIN productos AS p ON v.productoId = p.id
        WHERE v.timestamp BETWEEN :startTime AND :endTime
    """)
    fun getGananciaNetaEnRango(startTime: Long, endTime: Long): Flow<Double>

    // CONSULTA INGRESO BRUTO
    @Query("""
        SELECT COALESCE(SUM(cantidad * precioVentaAlMomento), 0.0)
        FROM ventas
        WHERE timestamp BETWEEN :startTime AND :endTime
    """)
    fun getIngresoBrutoEnRango(startTime: Long, endTime: Long): Flow<Double>
}