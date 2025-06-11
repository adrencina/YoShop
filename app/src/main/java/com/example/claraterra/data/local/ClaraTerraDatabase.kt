package com.example.claraterra.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.claraterra.data.local.dao.GastoDao
import com.example.claraterra.data.local.dao.ProductoDao
import com.example.claraterra.data.local.dao.VentaDao
import com.example.claraterra.data.local.entity.Gasto
import com.example.claraterra.data.local.entity.Producto
import com.example.claraterra.data.local.entity.Venta

@Database(entities = [Venta::class, Gasto::class, Producto::class], version = 2)
abstract class ClaraTerraDatabase : RoomDatabase() {
    abstract fun ventaDao(): VentaDao
    abstract fun gastoDao(): GastoDao
    abstract fun productoDao(): ProductoDao
}