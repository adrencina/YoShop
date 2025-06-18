package com.example.claraterra.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.claraterra.ui.theme.LocalSpacing

/** Composable wrapper que aplica el margin medium global */
@Composable
fun GlobalMargin(content: @Composable (Modifier) -> Unit) {
    val spacing = LocalSpacing.current
    content(Modifier.padding(spacing.medium))
}