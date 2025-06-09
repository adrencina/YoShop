package com.example.claraterra.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
fun CardItem(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    elevation: Dp = 4.dp,
    shape: androidx.compose.foundation.shape.CornerBasedShape = MaterialTheme.shapes.medium,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .padding(padding)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}
