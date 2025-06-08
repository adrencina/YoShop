package com.example.claraterra.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.claraterra.data.local.entity.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {
    @Insert
    suspend fun insertarGasto(gasto: Gasto)

    @Query("SELECT * FROM gastos")
    fun obtenerGastos(): Flow<List<Gasto>>
}