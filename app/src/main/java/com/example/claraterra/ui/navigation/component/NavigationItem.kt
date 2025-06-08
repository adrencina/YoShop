package com.example.claraterra.ui.navigation.component

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.claraterra.ui.navigation.NavigationRoute

data class NavigationItem(
    val route: NavigationRoute,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector
)