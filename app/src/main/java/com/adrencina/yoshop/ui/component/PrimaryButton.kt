package com.adrencina.yoshop.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    shape: androidx.compose.foundation.shape.CornerBasedShape = MaterialTheme.shapes.medium
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(padding),
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.onPrimary)
    }
}