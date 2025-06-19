package com.adrencina.yoshop.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adrencina.yoshop.ui.theme.LocalSpacing

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = spacing.medium,
                vertical = spacing.small
            ),
        verticalArrangement = Arrangement.Top
    ) {
        content()
    }
}
