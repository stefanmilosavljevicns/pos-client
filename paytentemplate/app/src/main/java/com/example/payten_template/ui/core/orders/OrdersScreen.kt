package com.example.payten_template.ui.core.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.payten_template.domain.Usluge
import com.example.payten_template.repositories.ReservationRepository
import com.example.payten_template.ui.navigation.Screen
import com.example.payten_template.payment.PaymentManager
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.payten_template.R
import com.example.payten_template.domain.Order
import com.example.payten_template.ui.shared.OrderListItem
import kotlinx.coroutines.launch

@Composable
fun OrdersScreen(
    archive: Boolean = false,
    navController: NavController,
    ordersViewModel: OrdersViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var orderToPay by remember {
        mutableStateOf<Order?>(null)
    }
    val paymentManager = PaymentManager(LocalContext.current)
    val orders by if(archive){
        ordersViewModel.archivedOrders.collectAsState(listOf())
    }else{
        ordersViewModel.orders.collectAsState(listOf())
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(8.dp), imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(text = "Porudzbine",  style = MaterialTheme.typography.h4)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Lista trenutnih porudzbina", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.size(16.dp))
            SwipeRefresh(
                modifier = Modifier
                    .defaultMinSize(minHeight = 200.dp)
                    .weight(1f),
                state = rememberSwipeRefreshState(ordersViewModel.isLoading),
                onRefresh = {
                    ordersViewModel.refresh()
                }) {
                LazyColumn(
                    content = {
                        items(orders){ order ->
                            OrderListItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                order = order,
                                onAccept = {
                                   scope.launch {
                                       ordersViewModel.acceptOrder(order)
                                       ordersViewModel.refresh()
                                   }
                                },
                                onDecline = {
                                    scope.launch {
                                        ordersViewModel.declineOrder(order)
                                        ordersViewModel.refresh()
                                    }
                                },
                                handlePayment = {
                                    orderToPay = it
                                }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    })
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = {
                navController.navigate(Screen.Rezervisi.route)
            }) {
            Icon(Icons.Filled.Add,"add reservation")
        }
        orderToPay?.let { order ->
            androidx.compose.ui.window.Dialog(onDismissRequest = { orderToPay = null }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        try{
                                            //rezervacija.placeno = true
                                            //ReservationRepository.Instance.updateReservation(rezervacija)

                                            ordersViewModel.refresh()
                                            /*paymentManager.requestPrintBill(
                                                order,
                                                order.price,
                                            )*/
                                            orderToPay = null
                                        }catch (ex: Exception){}
                                    }

                                },
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.height(96.dp),
                                painter = painterResource(id = R.drawable.ic_cash),
                                contentDescription = "cash",
                                contentScale = ContentScale.Fit,
                            )
                            Text(
                                text = "Plati gotovinom",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Column(
                            modifier = Modifier
                                .clickable {
                                    paymentManager.requestPayment(
                                        order.id,
                                        order.price,
                                        order.viberID
                                    )
                                    orderToPay = null
                                },
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.height(96.dp),
                                painter = painterResource(id = R.drawable.ic_credit_card),
                                contentDescription = "credit card",
                                contentScale = ContentScale.Fit,
                            )
                            Text(
                                text = "Plati kreditnom karticom",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}