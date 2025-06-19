package com.adrencina.yoshop.ui.products

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adrencina.yoshop.data.local.entity.Producto
import com.adrencina.yoshop.ui.theme.ClaraTerraTheme
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

    ClaraTerraTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Mis Productos", style = MaterialTheme.typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.onAddProductoClick() },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Productos")
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            if (productos.isEmpty()) {
                EmptyState(modifier = Modifier.padding(innerPadding))
            } else {
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { Spacer(Modifier.height(8.dp)) }
                    items(productos, key = { it.id }) { producto ->
                        ProductoListItem(
                            producto = producto,
                            onEditClick = { viewModel.onEditProductoClick(producto) },
                            onDeleteClick = { viewModel.onDeleteProducto(producto) }
                        )
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }

        if (uiState.isDialogVisible) {
            ProductEntryDialog(
                producto = uiState.productoSeleccionado,
                onDismiss = { viewModel.onDismissDialog() },
                onSave = { id, nombre, costo, venta, stock, descripcion, imagenUri ->
                    viewModel.onSaveProducto(id, nombre, costo, venta, stock, descripcion, imagenUri)
                }
            )
        }
    }
}

@Composable
private fun ProductoListItem(
    producto: Producto,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    val ganancia = producto.precioVenta - producto.precioCosto

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imagenUri)
                    .build(),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.background),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Stock: ${producto.stock} unidades",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (producto.stock > 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Ganancia: ${currencyFormatter.format(ganancia)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Column {
                IconButton(onClick = onEditClick) { Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary) }
                IconButton(onClick = onDeleteClick) { Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error) }
            }
        }
    }
}


@Composable
private fun ProductEntryDialog(
    producto: Producto?,
    onDismiss: () -> Unit,
    onSave: (id: Int?, nombre: String, costo: String, venta: String, stock: String, descripcion: String?, imagenUri: String?) -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var costo by remember { mutableStateOf(producto?.precioCosto?.toString() ?: "") }
    var venta by remember { mutableStateOf(producto?.precioVenta?.toString() ?: "") }
    var stock by remember { mutableStateOf(producto?.stock?.toString() ?: "0") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var imagenUri by remember { mutableStateOf<Uri?>(producto?.imagenUri?.let { Uri.parse(it) }) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(it, flags)
                imagenUri = it
            }
        }
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = if (producto == null) "Agregar Producto" else "Editar Producto",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 28.sp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text(if (imagenUri == null) "Elegir foto" else "Cambiar foto")
                }
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del producto") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción (opcional)") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = costo, onValueChange = { costo = it }, label = { Text("Costo \$") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                    OutlinedTextField(value = venta, onValueChange = { venta = it }, label = { Text("Venta \$") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock actual") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("CANCELAR", color = MaterialTheme.colorScheme.primary) }
                    Button(
                        onClick = { onSave(producto?.id, nombre, costo, venta, stock, descripcion, imagenUri?.toString()) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) { Text("GUARDAR") }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_manage),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "¡Hola, Clara!",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tus Productos aparecerán acá. ¡Tocá el '+' para empezar a organizar tu emprendimiento!",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}