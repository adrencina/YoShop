package com.example.claraterra.ui.screen.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.claraterra.data.local.entity.Producto
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManagementScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val productos by viewModel.productos.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Gestión de Productos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddProductoClick() }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productos, key = { it.id }) { producto ->
                ProductoListItem(
                    producto = producto,
                    onEditClick = { viewModel.onEditProductoClick(producto) },
                    onDeleteClick = { viewModel.onDeleteProducto(producto) }
                )
            }
        }
    }

    if (uiState.isDialogVisible) {
        ProductEntryDialog(
            producto = uiState.productoSeleccionado,
            onDismiss = { viewModel.onDismissDialog() },
            onSave = { nombre, costo, venta ->
                viewModel.onSaveProducto(nombre, costo, venta)
            }
        )
    }
}

@Composable
private fun ProductoListItem(
    producto: Producto,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    Card(elevation = CardDefaults.cardElevation(2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f).padding(vertical = 12.dp)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Costo: ${currencyFormatter.format(producto.precioCosto)} | Venta: ${currencyFormatter.format(producto.precioVenta)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun ProductEntryDialog(
    producto: Producto?,
    onDismiss: () -> Unit,
    onSave: (nombre: String, costo: String, venta: String) -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var costo by remember { mutableStateOf(producto?.precioCosto?.toString() ?: "") }
    var venta by remember { mutableStateOf(producto?.precioVenta?.toString() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = if (producto == null) "Nuevo Producto" else "Editar Producto",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del producto") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = costo,
                    onValueChange = { costo = it },
                    label = { Text("Precio de costo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = venta,
                    onValueChange = { venta = it },
                    label = { Text("Precio de venta") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("CANCELAR") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onSave(nombre, costo, venta) }) { Text("GUARDAR") }
                }
            }
        }
    }
}