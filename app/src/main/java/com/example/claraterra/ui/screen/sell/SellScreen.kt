package com.example.claraterra.ui.screen.sell

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.claraterra.R
import com.example.claraterra.data.local.entity.Producto
import com.example.claraterra.ui.component.GreetingTopBar
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SellViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val productos by viewModel.productosFiltrados.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GreetingTopBar(userName = "una nueva Venta")

        Text(
            text = "Seleccioná un producto de tu catálogo para registrar una nueva venta.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        OutlinedTextField(
            value = uiState.searchQuery, // <-- La forma simple y correcta de leer el valor
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Buscar producto...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            singleLine = true
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos, key = { it.id }) { producto ->
                ProductoVentaItem(
                    producto = producto,
                    onClick = { if (producto.stock > 0) viewModel.onProductoSeleccionado(producto) }
                )
            }
        }
    }

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
private fun ProductoVentaItem(producto: Producto, onClick: () -> Unit) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    val isOutOfStock = producto.stock <= 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isOutOfStock, onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isOutOfStock) MaterialTheme.colorScheme.surface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imagenUri)
                    .error(R.drawable.iconflower)
                    .build(),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.background)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Stock: ${producto.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isOutOfStock) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Text(
                text = currencyFormatter.format(producto.precioVenta),
                style = MaterialTheme.typography.titleMedium,
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
        Card(shape = MaterialTheme.shapes.large, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = producto.imagenUri,
                    contentDescription = producto.nombre,
                    modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.medium)
                )
                Spacer(Modifier.height(16.dp))
                Text(text = producto.nombre, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = "Stock disponible: ${producto.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onCantidadChange(uiState.cantidadVenta - 1) },
                        enabled = uiState.cantidadVenta > 1
                    ) {
                        Icon(Icons.Default.Remove, "Quitar uno")
                    }
                    Text(
                        text = "${uiState.cantidadVenta}",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    IconButton(
                        onClick = { onCantidadChange(uiState.cantidadVenta + 1) },
                        enabled = uiState.cantidadVenta < producto.stock
                    ) {
                        Icon(Icons.Default.Add, "Agregar uno")
                    }
                }
                Spacer(Modifier.height(16.dp))
                val total = producto.precioVenta * uiState.cantidadVenta
                Text(text = "Total: ${currencyFormatter.format(total)}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("CANCELAR") }
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("¡VENDIDO!")
                    }
                }
            }
        }
    }
}