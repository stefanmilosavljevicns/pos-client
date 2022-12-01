package com.example.payten_template.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.ui.text.capitalize
import com.example.payten_template.Data.Rezervacija
import com.example.payten_template.Data.Rezervacija.Companion.reservationDateFormat
import com.example.payten_template.ui.theme.ButtonColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Rezervacija(
    modifier: Modifier = Modifier,
    rezervacija: Rezervacija,
    onClick: () -> Unit = {}
){
    Card(
        modifier = modifier.clickable {
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(if (rezervacija.placeno) ButtonColor else Color.LightGray)
            .padding(16.dp)) {
            Column {
                val date = reservationDateFormat.parse(rezervacija.reservation)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = SimpleDateFormat("HH:mm").format(date),
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.ExtraBold,
                        color = if(rezervacija.placeno) Color.White else Color.Black,
                    )
                    //Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = SimpleDateFormat("dd.MM.yy").format(date),
                        style = MaterialTheme.typography.h6,
                        color = if(rezervacija.placeno) Color.White else Color.DarkGray,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = rezervacija.name,
                        style = MaterialTheme.typography.body1,
                        color = if(rezervacija.placeno) Color.White else Color.Black,
                    )
                    Text(
                        text = rezervacija.services?.capitalize() ?: "Sisanje",
                        style = MaterialTheme.typography.body1,
                        color = if(rezervacija.placeno) Color.White else Color.Black,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Rezervacija_Preview(){
    Column {
        Rezervacija(
            modifier = Modifier.height(100.dp),
            rezervacija = Rezervacija(
                id = "23ntio2vneir2neovrowienvfw",
                name = "Marko",
                reservation = reservationDateFormat.format(Date()),
                worker = "Rokan",
                placeno = false,
                telegram = false
            )
        )
    }
}