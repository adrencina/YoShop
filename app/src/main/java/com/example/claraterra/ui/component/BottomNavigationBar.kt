package com.example.claraterra.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.claraterra.ui.navigation.NavigationRoute
import com.example.claraterra.ui.navigation.component.NavigationItem

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
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
            modifier = Modifier
                .fillMaxWidth()
                .height(navigationBarHeight)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconSize = 32.dp

                items.forEach { item ->
                    val isSelected = currentRoute == item.route.route
                    val isSell = item.route.route == NavigationRoute.Sell.route

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .then(if (isSell) Modifier.offset(y = (-1).dp) else Modifier),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSell) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary,
                                shadowElevation = 5.dp,
                                tonalElevation = 4.dp,
                                modifier = Modifier.size(40.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        if (!isSelected) {
                                            navController.navigate(item.route.route) {
                                                popUpTo(NavigationRoute.Home.route) {
                                                    inclusive = false
                                                }
                                                launchSingleTop = true
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = item.filledIcon,
                                        contentDescription = item.route.route,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    if (!isSelected) {
                                        navController.navigate(item.route.route) {
                                            popUpTo(NavigationRoute.Home.route) {
                                                inclusive = false
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                },
                                modifier = Modifier.size(iconSize),
                                interactionSource = MutableInteractionSource()
                            ) {
                                Icon(
                                    imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                                    contentDescription = item.route.route,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}