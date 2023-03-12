package com.example.payten_template.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.payten_template.domain.Reservation
import com.example.payten_template.repositories.ReservationRepository

@Composable
fun RezervacijaScreen(
    navController: NavController,
    id: String
){
    var isLoading by remember {
        mutableStateOf(true)
    }
    var reservation by remember {
        mutableStateOf<Reservation?>(null)
    }
    var isFailed by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit){
        isLoading = true
        ReservationRepository.Instance.reservations.value.find {
            it.id == id
        }?.let {
            reservation = it
        } ?: kotlin.run {
            isFailed = true
        }
        isLoading = false
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(8.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back")
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(text = "Rezervacija", style = MaterialTheme.typography.h4)
                }
            }
            if(isLoading){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            if(isFailed){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            modifier = Modifier.size(64.dp),
                            imageVector = Icons.Filled.Close,
                            contentDescription = "failed",
                            tint = Color.Red
                        )
                        Text(text = "Nevalidan QR kod", color = Color.Red)
                    }
                }
            }
            reservation?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Vreme i datum: ${it.reservation}",
                            style = MaterialTheme.typography.h5
                        )
                        Text(
                            text = "Ime: ${it.name}",
                            style = MaterialTheme.typography.h5
                        )
                        Text(
                            text = "Usluga: ${it.services}",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }
    }
}