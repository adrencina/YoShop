package com.adrencina.yoshop.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.adrencina.yoshop.ui.navigation.NavigationRoute
import com.adrencina.yoshop.ui.navigation.component.NavigationItem

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun BottomNavigationBar(navController: NavController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val navigationBarHeight = screenHeight * 0.08f
    val horizontalPadding = 16.dp
    val bottomPadding = 8.dp

    val items = listOf(
        NavigationItem(NavigationRoute.Home, Icons.Outlined.Home, Icons.Filled.Home),
        NavigationItem(NavigationRoute.Supplies,Icons.Outlined.Storefront,Icons.Filled.Storefront),
        NavigationItem(NavigationRoute.Sell, Icons.Outlined.AddCircle, Icons.Filled.AddCircle),
        NavigationItem(NavigationRoute.Balance, Icons.Outlined.BarChart, Icons.Filled.BarChart),
        NavigationItem(NavigationRoute.Settings, Icons.Outlined.Settings, Icons.Filled.Settings)
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = bottomPadding)
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
            modifier = Modifier
                .fillMaxWidth()
                .height(navigationBarHeight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route.route
                    val isSell = item.route.route == NavigationRoute.Sell.route

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSell) {
                            FloatingActionButton(
                                onClick = { if (!isSelected) navController.navigate(item.route.route) },
                                shape = CircleShape,
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                elevation = FloatingActionButtonDefaults.elevation(2.dp),
                                modifier = Modifier.size(56.dp).offset(y = (-0.5).dp)
                            ) {
                                Icon(imageVector = item.filledIcon, contentDescription = item.route.route, modifier = Modifier.size(32.dp))
                            }
                        } else {
                            IconButton(onClick = { if (!isSelected) navController.navigate(item.route.route) }) {
                                Icon(
                                    imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                                    contentDescription = item.route.route,
                                    modifier = Modifier.size(28.dp),
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}