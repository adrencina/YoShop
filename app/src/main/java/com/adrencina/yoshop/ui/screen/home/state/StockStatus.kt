package com.adrencina.yoshop.ui.screen.home.state

enum class StockStatus {
    OK,      // Todo en orden
    LOW,     // Algunos productos tienen stock bajo
    CRITICAL, // Hay al menos un producto agotado
    EMPTY // No hay productos en stock
}