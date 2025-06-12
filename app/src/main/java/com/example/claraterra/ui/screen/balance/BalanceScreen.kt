package com.example.claraterra.ui.screen.balance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
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
    val textLosses = remember { mutableStateOf("Valor Gastos") }
    val textSales = remember { mutableStateOf("Valor Ventas") }
    val textProfit = remember { mutableStateOf("Valor Utilidad") }
    val textValBalanceTitle = remember { mutableStateOf("1") }
    val textBalanceTitle = "BALANCE DEL DIA: "
    val lossesDay = "Gastos del dia"
    val salesDay = "Ventas del dia"



    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.iconflower),
                    contentDescription = "app icon flower",
                    modifier = Modifier
                        .size(50.dp)
                )

                Spacer(modifier = Modifier.weight(2f))

                Image(
                    painter = painterResource(id = R.drawable.iconbalance),
                    contentDescription = "app icon balance",
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.weight(4f))

            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->

        ScreenContainer(modifier = Modifier.padding(innerPadding)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .background(Color.Gray)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.weight(0.2f))

                Row {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFF7D496A))
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Gastos $",
                        fontSize = 18.sp
                    )

                    Text(
                        text = textLosses.value,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(9f))

                }


                Row {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFF7D496A))
                            .align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Ventas $",
                        fontSize = 18.sp
                    )

                    Text(
                        text = textSales.value,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(9f))

                }

                Row {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFF7D496A))
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.weight(1f))


                    Text(
                        text = "Utilidad $",
                        fontSize = 18.sp
                    )

                    Text(
                        text = textProfit.value,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(9f))

                }
                Spacer(modifier = Modifier.weight(0.2f))

                Row {
                    Spacer(modifier = Modifier.weight(2f))

                    Text(
                        text = textBalanceTitle,
                        fontSize = 18.sp
                    )
                    Text(
                        text = textValBalanceTitle.value,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(2f))

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .paint(painterResource(id = R.drawable.arrowleft))
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.weight(0.5f))

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .paint(painterResource(id = R.drawable.arrowright))
                            .align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF7D496A))
                )


                Row (modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)){

                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)){

                        Text(
                            text = salesDay,
                            fontSize = 18.sp
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight(1f)
                                .background(color = Color.Black)
                        ) {

                        }
                    }
                    Spacer(modifier = Modifier.weight(0.05f))

                    Column (modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)){
                        Text(
                            text = lossesDay,
                            fontSize = 18.sp
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight(1f)
                                .background(color = Color.Black)
                        ) {

                        }
                    }


                }


            }

        }
    }
}