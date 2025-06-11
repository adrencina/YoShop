package com.example.claraterra.ui.screen.balance

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.claraterra.R
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.component.ScreenContainer


@Composable
fun BalanceScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val textRevenue = remember { mutableStateOf("Valor Ganancia") }

    Scaffold(
        modifier = modifier,
        topBar = {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier
                .weight(0.5f))

            Image(
                painter = painterResource(id = R.drawable.iconflower),
                contentDescription = "app icon flower",
                modifier = Modifier
                    .size(50.dp))

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.iconbalance),
                contentDescription = "app icon balance",
                modifier = Modifier.size(50.dp))

            Spacer(modifier = Modifier.weight(1.5f)            )

        }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->

        ScreenContainer(modifier = Modifier.padding(innerPadding)) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(20.dp),
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .background(Color.Black))

                Spacer(modifier = Modifier.weight(1.5f))

                Row {
                    Box(modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Red)
                    )
                    Text(
                        text = textRevenue.value,
                        fontSize = 18.sp
                    )

                }

                Spacer(modifier = Modifier.weight(0.5f))

                Row {
                    Box(modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Red)
                    )
                    Text(
                        text = textRevenue.value,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Row {
                    Box(modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Red)
                    )
                    Text(
                        text = textRevenue.value,
                        fontSize = 18.sp
                    )
                }


            }

        }
    }
}