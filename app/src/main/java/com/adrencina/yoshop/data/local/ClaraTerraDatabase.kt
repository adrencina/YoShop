package com.adrencina.yoshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adrencina.yoshop.data.local.dao.GastoDao
import com.adrencina.yoshop.data.local.dao.ProductoDao
import com.adrencina.yoshop.data.local.dao.VentaDao
import com.adrencina.yoshop.data.local.entity.Gasto
import com.adrencina.yoshop.data.local.entity.Producto
import com.adrencina.yoshop.data.local.entity.Venta

@Database(entities = [Venta::class, Gasto::class, Producto::class], version = 3)
abstract class ClaraTerraDatabase : RoomDatabase() {
    abstract fun ventaDao(): VentaDao
    abstract fun gastoDao(): GastoDao
    abstract fun productoDao(): ProductoDao
}