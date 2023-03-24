package com.example.payten_template.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.payten_template.domain.Order
import com.example.payten_template.domain.OrderState
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun OrderListItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    order: Order,
    onAccept: () -> Unit = {},
    onDecline: () -> Unit = {},
    handlePayment: (Order) -> Unit = {},
) {
    val backgroundColor: Color = when(order.state){
        OrderState.PENDING -> {
            Color.LightGray.copy(alpha = 0.7f)
        }
        OrderState.IN_PROGRESS -> {
            Color.Green.copy(alpha = 0.3f)
        }
        OrderState.COMPLETED -> {
            Color.Blue.copy(alpha = 0.3f)
        }
        OrderState.DECLINED -> {
            Color.Red.copy(alpha = 0.4f)
        }
    }
    Column(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Opis:",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = order.description.joinToString { it + "\n" }.removeSuffix("\n"),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                val dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd HH:mm")
                val startTime = order.getStartTime()?.format(dateTimeFormatter) ?: order.startTime
                val endTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                val endTime = order.getEndTime()?.format(endTimeFormatter) ?: order.endTime
                Text(
                    text = "$startTime-$endTime",
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        "Cena: ",
                        style = MaterialTheme.typography.subtitle2,
                    )
                    Text(
                        text = "%.2f RSD".format(order.price),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
        // Show buttons
        when(order.state){
            OrderState.PENDING -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        )
                        ,onClick = {
                            onDecline()
                        }
                    ) {
                        Text("Odbij")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF008000),
                            contentColor = Color.White
                        ),
                        onClick = {
                            onAccept()
                        }) {
                        Text("Prihvati")
                    }
                }
            }
            OrderState.IN_PROGRESS -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OrderState(order)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF008000),
                            contentColor = Color.White
                        ),
                        onClick = {
                            handlePayment(order)
                        }) {
                        Text("Naplati")
                    }
                }
            }
            else -> {
                OrderState(order)
            }
        }
    }
}

@Composable
private fun OrderState(order: Order){
    Text(
        text = order.state.description,
        style = MaterialTheme.typography.h5
    )
}

@Preview
@Composable
fun OrderListItem_Preview(){
    val orders = listOf(
        Order(
            id = UUID.randomUUID().toString(),
            description = arrayOf("2 velike porodicke kapricoze"),
            price = 2500.0,
            state = OrderState.PENDING,
            endTime = "2023-02-05T14:30:00",
            startTime = "2023-02-05T17:30:00",
            viberID = "viberuser2"
        ),
        Order(
            id = UUID.randomUUID().toString(),
            description = arrayOf("2 velike porodicke kapricoze"),
            price = 2500.0,
            state = OrderState.IN_PROGRESS,
            endTime = "2023-02-05T14:30:00",
            startTime = "2023-02-05T17:30:00",
            viberID = "viberuser2"
        ),
        Order(
            id = UUID.randomUUID().toString(),
            description = arrayOf("2 velike porodicke kapricoze"),
            price = 2500.0,
            state = OrderState.COMPLETED,
            endTime = "2023-02-05T14:30:00",
            startTime = "2023-02-05T17:30:00",
            viberID = "viberuser2"
        ),
        Order(
            id = UUID.randomUUID().toString(),
            description = arrayOf("2 velike porodicke kapricoze"),
            price = 2500.0,
            state = OrderState.DECLINED,
            endTime = "2023-02-05T14:30:00",
            startTime = "2023-02-05T17:30:00",
            viberID = "viberuser2"
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        orders.forEach { order ->
            OrderListItem(order = order)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}