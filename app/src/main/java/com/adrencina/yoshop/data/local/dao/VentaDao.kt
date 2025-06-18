package com.adrencina.yoshop.data.local.dao

import androidx.room.*
import com.adrencina.yoshop.data.local.entity.Producto
import com.adrencina.yoshop.data.local.entity.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Insert
    suspend fun insertarVenta(venta: Venta)

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Transaction
    suspend fun registrarVentaYActualizarStock(venta: Venta, productoActualizado: Producto) {
        insertarVenta(venta)
        actualizarProducto(productoActualizado)
    }

    // --- CAMBIO: Se reemplazó "fecha" por "timestamp" ---
    @Query("SELECT * FROM ventas WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun obtenerVentasDetalladasEnRango(startTime: Long, endTime: Long): Flow<List<Venta>>

    @Query("""
        SELECT COALESCE(SUM(v.cantidad * p.precioVenta), 0.0) 
        FROM ventas v JOIN productos p ON v.productoId = p.id 
        WHERE v.timestamp BETWEEN :startTime AND :endTime
    """)
    fun getIngresoBrutoEnRango(startTime: Long, endTime: Long): Flow<Double>

    @Query("""
        SELECT COALESCE(SUM(v.cantidad * (p.precioVenta - p.precioCosto)), 0.0) 
        FROM ventas v JOIN productos p ON v.productoId = p.id 
        WHERE v.timestamp BETWEEN :startTime AND :endTime
    """)
    fun getGananciaNetaEnRango(startTime: Long, endTime: Long): Flow<Double>
}