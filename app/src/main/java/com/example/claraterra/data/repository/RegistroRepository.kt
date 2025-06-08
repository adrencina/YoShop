package com.example.claraterra.data.repository

import com.example.claraterra.data.local.dao.GastoDao
import com.example.claraterra.data.local.dao.VentaDao
import com.example.claraterra.data.local.entity.Gasto
import com.example.claraterra.data.local.entity.Venta
import javax.inject.Inject

class RegistroRepository @Inject constructor(
    private val ventaDao: VentaDao,
    private val gastoDao: GastoDao
) {
//    suspend fun registrarVenta(venta: Venta) = ventaDao.insert(venta)
//    suspend fun registrarGasto(gasto: Gasto) = gastoDao.insert(gasto)
}