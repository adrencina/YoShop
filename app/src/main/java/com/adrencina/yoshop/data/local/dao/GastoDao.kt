package com.adrencina.yoshop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adrencina.yoshop.data.local.entity.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {
    @Insert
    suspend fun insertarGasto(gasto: Gasto)

    // --- CAMBIO: Se reemplazó "fecha" por "timestamp" ---
    @Query("SELECT COALESCE(SUM(monto), 0.0) FROM gastos WHERE timestamp BETWEEN :startTime AND :endTime")
    fun getTotalGastosEnRango(startTime: Long, endTime: Long): Flow<Double>

    @Query("SELECT * FROM gastos WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun obtenerGastosDetalladosEnRango(startTime: Long, endTime: Long): Flow<List<Gasto>>
}