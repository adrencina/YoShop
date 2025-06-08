package com.example.claraterra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descripcion: String,
    val monto: Double,
    val fecha: String
)