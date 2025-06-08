package com.example.claraterra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val producto: String,
    val cantidad: Int,
    val precioUnitario: Double
)