package com.example.claraterra.ui.screen.sell

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.component.ScreenContainer
import kotlinx.coroutines.flow.map
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SellScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SellViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val productos by viewModel.productosFiltrados.collectAsStateWithLifecycle()
    val searchQuery by viewModel.uiState.map { it.searchQuery }.collectAsStateWithLifecycle("")


    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        ScreenContainer(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Buscador
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Buscar producto...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    singleLine = true
                )

                // Lista de productos
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productos, key = { it.id }) { producto ->
                        ProductoItem(
                            producto = producto,
                            onClick = { viewModel.onProductoSeleccionado(producto) }
                        )
                    }
                }
            }
        }
    }

    // Diálogo de confirmación
    if (uiState.productoSeleccionado != null) {
        VentaConfirmationDialog(
            uiState = uiState,
            onDismiss = { viewModel.onDismissDialog() },
            onCantidadChange = { viewModel.onCantidadChange(it) },
            onConfirm = { viewModel.onConfirmarVenta() }
        )
    }
}

@Composable
private fun ProductoItem(producto: com.example.claraterra.data.local.entity.Producto, onClick: () -> Unit) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = producto.nombre, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = currencyFormatter.format(producto.precioVenta),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun VentaConfirmationDialog(
    uiState: SellUiState,
    onDismiss: () -> Unit,
    onCantidadChange: (Int) -> Unit,
    onConfirm: () -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    val producto = uiState.productoSeleccionado ?: return

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Selector de cantidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onCantidadChange(uiState.cantidadVenta - 1) }) {
                        Icon(Icons.Default.Remove, contentDescription = "Quitar uno")
                    }
                    Text(
                        text = "${uiState.cantidadVenta}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    IconButton(onClick = { onCantidadChange(uiState.cantidadVenta + 1) }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar uno")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Total
                val total = producto.precioVenta * uiState.cantidadVenta
                Text(
                    text = "Total: ${currencyFormatter.format(total)}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("CANCELAR")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onConfirm) {
                        Text("CONFIRMAR VENTA")
                    }
                }
            }
        }
    }
}