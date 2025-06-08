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

    @Query("SELECT * FROM ventas")
    fun obtenerVentas(): Flow<List<Venta>>
}