package com.adrencina.yoshop.ui.balance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.adrencina.yoshop.data.local.entity.Gasto
import com.adrencina.yoshop.data.local.entity.Venta
import com.adrencina.yoshop.ui.common.GreetingTopBar
import com.adrencina.yoshop.viewmodel.BalanceViewModel
import com.adrencina.yoshop.viewmodel.Periodo
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BalanceScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BalanceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GreetingTopBar(userName = "tus Finanzas")

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            PeriodSelector(
                periodoActual = uiState.periodo,
                onPeriodoSeleccionado = { viewModel.cambiarPeriodo(it) }
            )

            ResumenBalance(
                ingresos = currencyFormatter.format(uiState.totalIngresos),
                gastos = currencyFormatter.format(uiState.totalGastos),
                ganancia = currencyFormatter.format(uiState.gananciaNeta)
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetalleColumna(
                    modifier = Modifier.weight(1f),
                    titulo = "Últimas Ventas",
                    ventas = uiState.ultimasVentas,
                    formatter = currencyFormatter
                )
                DetalleColumna(
                    modifier = Modifier.weight(1f),
                    titulo = "Últimos Gastos",
                    gastos = uiState.ultimosGastos,
                    formatter = currencyFormatter
                )
            }
        }
    }
}

// --- Componentes Auxiliares ---

@Composable
fun PeriodSelector(periodoActual: Periodo, onPeriodoSeleccionado: (Periodo) -> Unit) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Periodo.values().forEach { periodo ->
            SegmentedButton(
                selected = periodo == periodoActual,
                onClick = { onPeriodoSeleccionado(periodo) },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(periodo.name)
            }
        }
    }
}

@Composable
fun ResumenBalance(ingresos: String, gastos: String, ganancia: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(Modifier.weight(1f), "Ingresos", ingresos, MaterialTheme.colorScheme.primary)
        StatCard(Modifier.weight(1f), "Gastos", gastos, MaterialTheme.colorScheme.error)
        StatCard(Modifier.weight(1f), "Ganancia", ganancia, MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Card(modifier = modifier) {
        Column(Modifier.padding(12.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelMedium)
            Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun DetalleColumna(
    modifier: Modifier = Modifier,
    titulo: String,
    formatter: NumberFormat,
    ventas: List<Venta>? = null,
    gastos: List<Gasto>? = null
) {
    val dateFormatter = remember { SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()) }

    Column(modifier = modifier) {
        Text(text = titulo, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ventas?.let {
                items(it, key = { venta -> venta.id }) { venta ->
                    // --- ¡ACÁ ESTÁ LA CORRECCIÓN! ---
                    val montoTotalVenta = venta.cantidad * venta.precioVentaAlMomento
                    DetalleItem(
                        linea1 = formatter.format(montoTotalVenta),
                        linea2 = dateFormatter.format(Date(venta.timestamp)) // Usamos 'timestamp'
                    )
                }
            }
            gastos?.let {
                items(it, key = { gasto -> gasto.id }) { gasto ->
                    DetalleItem(
                        linea1 = gasto.descripcion,
                        linea2 = formatter.format(gasto.monto),
                        isGasto = true
                    )
                }
            }
        }
    }
}

@Composable
fun DetalleItem(linea1: String, linea2: String, isGasto: Boolean = false) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(
                text = linea1,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (isGasto) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = linea2,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
    }
}