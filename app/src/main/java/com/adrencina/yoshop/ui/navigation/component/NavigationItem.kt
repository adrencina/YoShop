package com.adrencina.yoshop.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.adrencina.yoshop.ui.navigation.NavigationRoute

data class NavigationItem(
    val route: NavigationRoute,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector
)