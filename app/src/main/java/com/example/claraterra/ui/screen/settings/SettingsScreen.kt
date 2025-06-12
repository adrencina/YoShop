package com.example.claraterra.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.theme.ClaraTerraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var showGoalDialog by remember { mutableStateOf(false) }
    var weeklyGoal by remember { mutableStateOf("50000") }
    var isDarkTheme by remember { mutableStateOf(false) }

    ClaraTerraTheme(darkTheme = isDarkTheme) { // El tema ahora responde al Switch
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Configuración") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = { BottomNavigationBar(navController = navController) },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // --- SECCIÓN DE PERSONALIZACIÓN ---
                item {
                    SectionHeader("Personalización")
                    SettingRow(
                        title = "Objetivo de ingreso semanal",
                        subtitle = "Tu meta actual es de $$weeklyGoal",
                        icon = { Icon(Icons.Default.TrackChanges, contentDescription = "Objetivo") },
                        onClick = { showGoalDialog = true }
                    )
                    SettingRow(
                        title = "Tema oscuro",
                        subtitle = if (isDarkTheme) "Activado" else "Desactivado",
                        icon = { Icon(Icons.Default.Brightness4, contentDescription = "Tema") },
                        onClick = { isDarkTheme = !isDarkTheme } // También clickeable
                    ) {
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { isDarkTheme = it }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // --- SECCIÓN DE CUENTA Y DATOS ---
                item {
                    SectionHeader("Cuenta y Datos")
                    SettingRow(
                        title = "Exportar datos",
                        subtitle = "Guardá un resumen de tus ventas y productos en un archivo.",
                        icon = { Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = "Exportar") },
                        onClick = { /* Lógica de exportación futura */ }
                    )
                    SettingRow(
                        title = "Cerrar sesión",
                        subtitle = "Salí de tu cuenta de forma segura.",
                        icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar Sesión") },
                        onClick = { /* Lógica de logout futura */ }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // --- SECCIÓN 'ACERCA DE' ---
                item {
                    SectionHeader("Acerca de")
                    SettingRow(
                        title = "Versión de la aplicación",
                        subtitle = "1.0.0 (MVP)",
                        icon = { Icon(Icons.Default.Info, contentDescription = "Versión") },
                        onClick = {}
                    )
                }
            }
        }
    }

    if (showGoalDialog) {
        GoalEntryDialog(
            currentGoal = weeklyGoal,
            onDismiss = { showGoalDialog = false },
            onSave = { newGoal ->
                weeklyGoal = newGoal
                showGoalDialog = false
            }
        )
    }
}

// --- COMPONENTES AUXILIARES PARA LA PANTALLA DE SETTINGS ---

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun SettingRow(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(32.dp)) {
                icon()
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            if (trailingContent != null) {
                Box(modifier = Modifier.size(48.dp)) {
                    trailingContent()
                }
            }
        }
    }
}

@Composable
private fun GoalEntryDialog(
    currentGoal: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(currentGoal) }

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = MaterialTheme.shapes.large) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Nuevo Objetivo Semanal", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Monto del objetivo") },
                    prefix = { Text("$") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("CANCELAR") }
                    Button(onClick = { onSave(textValue) }) { Text("GUARDAR") }
                }
            }
        }
    }
}