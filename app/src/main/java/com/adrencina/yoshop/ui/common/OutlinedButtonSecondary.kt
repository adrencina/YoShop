package com.adrencina.yoshop.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = 12.dp,
    shape: androidx.compose.foundation.shape.CornerBasedShape = MaterialTheme.shapes.small
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.padding(padding),
        shape = shape,
        border = ButtonDefaults.outlinedButtonBorder,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(text = text)
    }
}