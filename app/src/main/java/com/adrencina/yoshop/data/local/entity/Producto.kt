package com.adrencina.yoshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precioCosto: Double,
    val precioVenta: Double,
    val descripcion: String? = null,
    val stock: Int = 0,
    val imagenUri: String? = null
)