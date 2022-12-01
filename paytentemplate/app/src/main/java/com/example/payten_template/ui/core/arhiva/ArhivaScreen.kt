package com.example.payten_template
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.payten_template.ui.core.arhiva.ArhivaViewModel
import com.example.payten_template.ui.shared.Rezervacija
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArhivaScreen(
    navController: NavController,
    arhivaViewModel: ArhivaViewModel = viewModel()
) {
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
                    Text(text = "Arhiva",  style = MaterialTheme.typography.h4)
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Istorija rezervacija", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.size(16.dp))
            if(arhivaViewModel.rezervacije.size > 0){
                SwipeRefresh(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 200.dp)
                        .weight(1f),
                    state = rememberSwipeRefreshState(arhivaViewModel.isLoading),
                    onRefresh = {
                        arhivaViewModel.refresh()
                    }) {
                    LazyColumn(
                        content = {
                            var date: LocalDate? = null
                            items(arhivaViewModel.rezervacije){ rezervacija ->
                                /*val newDate = rezervacija.reservation.rezervationDate()!!.toLocalDate()
                                if(date == null){
                                    Text(text = newDate.toString())
                                    Spacer(modifier = Modifier.size(8.dp))
                                    date = newDate
                                }else{
                                    if(newDate.isAfter(date)){
                                        Text(text = newDate.toString())
                                        Spacer(modifier = Modifier.size(8.dp))
                                        date = newDate
                                    }
                                }*/
                                Rezervacija(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    rezervacija = rezervacija,
                                    onClick = {
                                    }
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        })
                }
            }else{
                Text("Nema rezervacija")
            }

        }
    }

}